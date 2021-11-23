package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenu;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenuPermission;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleMenuPermissionDao extends BaseDao<RoleMenuPermission> {
    RoleMenuPermission findByRoleMenuAndPermission(RoleMenu roleMenu, Permission permission);
    @Query("select r from RoleMenuPermission r where r.roleMenu = ?1")
    List<RoleMenuPermission> findRoleMenuPermissionByRoleMenu(RoleMenu roleMenu);
}
