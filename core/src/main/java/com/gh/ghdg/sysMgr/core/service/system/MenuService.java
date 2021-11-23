package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.cache.redis.RedisUtil;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.bean.enums.TypeEnum;
import com.gh.ghdg.sysMgr.core.dao.system.*;
import com.gh.ghdg.sysMgr.core.service.TreeService;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.security.JwtUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService extends TreeService<Menu, MenuDao> {
    
    @Autowired
    private UserMenuDao userMenuDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoleMenuService roleMenuService;
    
    @Autowired
    private RoleMenuDao roleMenuDao;
    
    @Autowired
    private RoleMenuPermissionDao roleMenuPermissionDao;
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RedisUtil cache;
    
    /**
     * 保存
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public Menu save(Menu t) throws Exception{
        return super.save(t);
    }
    
    /**
     * 删除菜单(未分配权限与角色)
     * 且同时删除中间表UserMenu所关联的行(手动维护表的关联关系)
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public List<Menu> delete(Menu t) {
        if(t.getPermissions().size()>0){
            throw new MyException("存在权限未删除");
        }
        if(t.getRoleMenus().size()>0){
            throw new MyException("已分配角色");
        }
        for(UserMenu um : t.getUserMenus()) {
            userMenuDao.delete(um);
        }
        return super.delete(t);
    }
    
    /**
     * 返回树根
     * @return
     */
    public List<Menu> tree(){
        List<Menu> tree = super.tree();
        clearPerms(tree);
        return tree;
    }
    
    /**
     * 清权限：递归，以提高性能
     * @param menus
     */
    private void clearPerms(List<Menu> menus) {
        for (Menu m : menus) {
            m.getPermissions().clear();
            clearPerms(m.getChildren0());
        }
    }
    
    /**
     * 保存分配角色
     * @param t
     * @param roleIds
     * @param permissions
     */
    @Transactional
    public void saveRoles(Menu t, String roleIds, String permissions) {
        if(StrUtil.isNotEmpty(roleIds)) {
            /**
             * 可以同时传入多个角色，一个一个来进行保存，而且权限也是根据","来分组，一组一组分配
             * 每组权限，组内权限使用"|"来分割
             */
            String[] roleIdArr = StrUtil.split(roleIds,",");
            String[] permissionArr = StrUtil.split(permissions,",");
            for(int i = 0; i < roleIdArr.length; i++) {
                String roleId = roleIdArr[i];
                Optional<Role> opt = roleDao.findById(roleId);
                if(!opt.isPresent()) {
                    throw new MyException("ID " +  roleId + " 角色不存在");
                }
                Role role = opt.get();
                roleMenuService.save(role, t, permissionArr[i]);
            }
        }
    }
    
    /**
     * 删除分配角色
     * @param t
     * @param roleIds
     */
    public void deleteRoles(Menu t, String roleIds) {
        if(StrUtil.isNotEmpty(roleIds)) {
            for(String roleId : StrUtil.split(roleIds,",")) {
                Role r = new Role();
                r.setId(roleId);
                RoleMenu rm = roleMenuDao.findByRoleAndMenu(r, t);
                for(RoleMenuPermission rmp : rm.getRoleMenuPermissions()) {
                    roleMenuPermissionDao.delete(rmp);
                }
                if(null != rm) {
                    roleMenuDao.delete(rm);
                }
            }
        }
    }
    
    /**
     * 角色的菜单树
     * @param roleId
     * @return
     */
    public List<Menu> tree4Role(String roleId) {
        if(roleId==null|| TreeEntity.ROOT_ID.equals(roleId)) {
            return null;
        }

        List<Menu> selected = Lists.newArrayList();
        Role r = roleDao.getById(roleId);
        for(RoleMenu rm : r.getRoleMenus()) {
            Menu m = rm.getMenu();

            List<Permission> perms = Lists.newArrayList();
            for(RoleMenuPermission rmp : rm.getRoleMenuPermissions()) {
                perms.add(rmp.getPermission());
            }
            permissionService.sort(perms);
            m.setAuthPermissions(perms);

            selected.add(m);
        }

        List<Menu> roots = asTreeCheckable(selected);
        recurSort(roots);
        return roots;
    }
    
    /**
     * 可选菜单树
     * @return
     * @param roleId
     */
    public List<Menu> treeSelectable(String roleId) {
        if(StrUtil.isEmpty(roleId)) {
            return null;
        }
        
        List<Menu> selectable = dao.findAll();
        Role r = roleDao.getById(roleId);
        for(RoleMenu rm : r.getRoleMenus()) {
            selectable.remove(rm.getMenu());
        }
        
        List<Menu> roots = asTreeCheckable(selectable);
        recurSort(roots);
        recurSortOpts(roots);
        return roots;
    }
    
    /**
     * 排序操作
     * @param menus
     */
    private void recurSortOpts(List<Menu> menus) {
        for(Menu m : menus) {
            permissionService.sort(m.getPermissions());
            recurSortOpts(m.getChildren0());
        }
    }
    
    /**
     * 导航
     * @return
     */
    public List<Menu> navigation() {
        List<Menu> roots = cache.get(Constants.CUR_MENU_ROOTS);
        if(null == roots) {
            roots = getCur();
        }
        return roots;
    }

    /**
     * 当前用户
     * @return
     */
    protected List<Menu> getCur() {
        List<Menu> menus = Lists.newArrayList();
        for(UserRole ur :  userRoleDao.findByUser(JwtUtil.getCurUser())) {
            for(RoleMenu rm : ur.getRole().getRoleMenus()) {
                Menu m = rm.getMenu();
                if(m.getType().equals(TypeEnum.MenuType.menu) && !menus.contains(m)) {
                    m.getPermissions().clear();
                    menus.add(m);
                }
                for(RoleMenuPermission rmp : rm.getRoleMenuPermissions()) {
                    if(!m.getPermissions().contains(rmp.getPermission())) {
                        m.getPermissions().add(rmp.getPermission());
                    }
                }
            }
        }

        List<Menu> roots = asTree(menus);
        recurSort(roots);
        recurNavigation(roots, menus);

        cache.set(Constants.CUR_MENUS,menus);
        cache.set(Constants.CUR_MENU_ROOTS,roots);

        return roots;
    }
    
    /**
     * 递归导航
     * @param menus
     * @param auths
     */
    private void recurNavigation(List<Menu> menus, List<Menu> auths) {
        for(Menu m : menus) {
            m.setNavigation(true);   // 打标志
            if(!auths.contains(m)) { // 非分配去 Permissions
                m.getPermissions().clear();
            }
            recurNavigation(m.getChildren0(), auths);
        }
    }
    
}
