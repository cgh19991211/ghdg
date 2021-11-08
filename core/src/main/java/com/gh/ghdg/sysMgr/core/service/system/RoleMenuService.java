package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.PermissionDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleMenuService extends BaseService<RoleMenu, RoleMenuDao> {
    @Autowired
    private RoleMenuPermissionDao roleMenuPermissionDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private PermissionDao permissionDao;
    
    /**
     * 前端传入角色id，菜单id，以及对应位置权限的true|false
     * 这里不对，与前端太过耦合。太依赖前端决定的顺序了。
     *
     * 需要做拆分，把权限分配给角色这个功能拆分出来。
     * 前端从传入对应的权限的位置，改为传入id
     * @param role
     * @param t
     * @param permissions 例： "user:cud|user:menu:cud"
     */
    @Transactional
    public void save(Role role, Menu t, String permissions) {
        Optional<Menu> menuOpt = menuDao.findById(t.getId());
        Menu menu = menuOpt.get();
        RoleMenu roleMenu = dao.findByRoleAndMenu(role, menu);
        if(null == roleMenu) {
            roleMenu = new RoleMenu();
            roleMenu.setRole(role);
            roleMenu.setMenu(menu);
            dao.save(roleMenu);
        }
        String[] permissionArr = StrUtil.split(permissions,"|");
        for(String str:permissionArr){
            Permission byPermissionCode = permissionDao.findByPermissionCode(str);
            RoleMenuPermission byRoleMenuAndPermission = roleMenuPermissionDao.findByRoleMenuAndPermission(roleMenu, byPermissionCode);
            if(byRoleMenuAndPermission==null){
                byRoleMenuAndPermission = new RoleMenuPermission();
                byRoleMenuAndPermission.setRoleMenu(roleMenu);
                byRoleMenuAndPermission.setPermission(byPermissionCode);
                roleMenuPermissionDao.save(byRoleMenuAndPermission);
            }
        }
    }
    
    /**
     * 查询角色所分配的菜单
     * @param t
     * @return
     */
    public List<Menu> list(Role t){
        List<RoleMenu> byRole = dao.findByRole(t);
        ArrayList<Menu> list = new ArrayList<>();
        for(RoleMenu rm:byRole){
            list.add(rm.getMenu());
        }
        return list;
    }
    
    /**
     * 查询菜单拥有的角色
     * @param t
     * @return
     */
    public List<Role> list(Menu t){
        List<RoleMenu> byRole = dao.findByMenu(t);
        ArrayList<Role> list = new ArrayList<>();
        for(RoleMenu rm:byRole){
            list.add(rm.getRole());
        }
        return list;
    }
}
