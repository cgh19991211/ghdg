package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.core.dao.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService extends BaseService<Role, RoleDao> {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MenuDao menuDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private RoleMenuService roleMenuService;
    
    @Autowired
    private RoleMenuPermissionService roleMenuPermissionService;
    
    @Autowired
    private PermissionDao permissionDao;

    @Transactional
    public List<Role> findByRoleName(String name){
        return dao.findByRoleName(name);
    }
    
    /**
     * 完整树
     * @param t
     * @return
     */
    public List<Role> tree() {
        return getRoots();
    }
    
    /**
     * 返回所有根角色--父角色为null
     * @param t
     * @return
     */
    protected List<Role> getRoots() {
        SearchFilter filter = SearchFilter.build("parent", SearchFilter.Operator.ISNULL);
        return queryAll(filter);
    }
    
    /**
     * 分配角色权限 (先清空后分配)
     * @param roleId
     * @param permissionIds
     */
    @Transactional
    public void setMenusAndPermissions(String roleId,String permissionIds){
        Role role = dao.getById(roleId);
        roleMenuService.clear(role);
        String[] permissions = StrUtil.split(permissionIds, ",");
        for(String pid:permissions){
            if(StrUtil.isEmpty(pid))continue;
            Permission permission = permissionDao.getById(pid);
            Menu menu = permission.getMenu();
            roleMenuService.save(role,menu,permission);
        }
    }
    
    
    /**
     * 根据用户id为用户分配角色
     * @param t
     * @param userIds
     */
    @Transactional
    public void assignUsers(Role t, String userIds){
        if(StrUtil.isNotBlank(userIds)){
            for(String id:StrUtil.split(userIds,",")){
                Optional<User> byId = userDao.findById(id);
                if(!byId.isPresent()){
                    throw new MyException("ID: "+id+" 用户不存在");
                }
                User user = byId.get();
                userRoleService.save(user,t);
            }
        }
    }
    
    /**
     * 回收分配的角色
     * @param t
     * @param userIds
     */
    @Transactional
    public void recycleUsers(Role t,String userIds){
        if(StrUtil.isNotBlank(userIds)){
            for (String id:StrUtil.split(userIds,",")){
                User user = new User();
                user.setId(id);
                UserRole ur = userRoleDao.findByUserAndRole(user,t);
                if(ur!=null){
                    userRoleDao.delete(ur);
                }
            }
        }
    }
    
    /**
     * 用户角色树
     * @param userId
     * @return
     */
    public List<Role> tree4User(String userId){
        User byId = userDao.getById(userId);
        List<UserRole> userRoles = byId.getUserRoles();
        List<Role> res = new ArrayList<>();
        for(UserRole ur:userRoles){
            res.add(ur.getRole());
        }
        return res;
    }
    
    /**
     * 返回未分配给该用户的角色
     * @param userId
     * @return
     */
    public List<Role> selectableTree4User(String userId){
        User byId = userDao.getById(userId);
        List<UserRole> userRoles = byId.getUserRoles();
        List<Role> res = dao.findAll();
        for(UserRole ur:userRoles){
            res.remove(ur.getRole());
        }
        return res;
    }
    
    /**
     * 菜单角色树
     * @param menuId
     * @return 根据菜单id返回所属的角色
     */
    public List<Role> tree4Menu(String menuId){
        Menu byId = menuDao.getById(menuId);
        List<RoleMenu> roleMenus = byId.getRoleMenus();
        List<Role> res = new ArrayList<>();
        for(RoleMenu rm:roleMenus){
            res.add(rm.getRole());
        }
        return res;
    }
    
    /**
     * 菜单可选角色树
     * @param menuId
     * @return 根据菜单id返回该未分配给该菜单的角色
     */
    public List<Role> selectableTree4Menu(String menuId){
        Menu byId = menuDao.getById(menuId);
        List<RoleMenu> roleMenus = byId.getRoleMenus();
        List<Role> res = dao.findAll();
        for(RoleMenu rm:roleMenus){
            res.remove(rm.getRole());
        }
        return res;
    }

}
