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
    
    /**
     * 先清除再保存
     * @param role
     * @param permission
     */
    @Transactional
    public void save(Role role, Menu menu, Permission permission) {
//        RoleMenu roleMenu = dao.findByRoleAndMenu(role, menu);
//        if(roleMenu!=null){
//            roleMenuPermissionDao.deleteRoleMenuPermissionByRoleMenuAndPermission(roleMenu,permission);
//            dao.delete(roleMenu);
//        }else {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(role);
            roleMenu.setMenu(menu);
            dao.save(roleMenu);
//        }
        RoleMenuPermission byRoleMenuAndPermission = roleMenuPermissionDao.findByRoleMenuAndPermission(roleMenu, permission);
//        if(byRoleMenuAndPermission==null){
            byRoleMenuAndPermission = new RoleMenuPermission();
            byRoleMenuAndPermission.setRoleMenu(roleMenu);
            byRoleMenuAndPermission.setPermission(permission);
            roleMenuPermissionDao.save(byRoleMenuAndPermission);
//        }
    }
    
    /**
     * 清空角色权限
     * @param role
     */
    @Transactional
    public void clear(Role role){
        //查找所有以分配给该角色的菜单
        List<RoleMenu> roleMenuList = dao.findByRole(role);
        if(roleMenuList==null||roleMenuList.size()==0)return;
        
        //删除该角色所有已分配的权限资源
        for(RoleMenu roleMenu:roleMenuList)
            roleMenuPermissionDao.deleteRoleMenuPermissionsByRoleMenu(roleMenu);
        
        //删除所有分配给该角色的菜单
        dao.deleteRoleMenusByRole(role);
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
