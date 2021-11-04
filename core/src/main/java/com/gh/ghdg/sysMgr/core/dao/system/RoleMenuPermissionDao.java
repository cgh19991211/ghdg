package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.common.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenu;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenuPermission;

public interface RoleMenuPermissionDao extends BaseDao<RoleMenuPermission> {
    RoleMenuPermission findByRoleMenuAndPermission(RoleMenu roleMenu, Permission permission);
}
