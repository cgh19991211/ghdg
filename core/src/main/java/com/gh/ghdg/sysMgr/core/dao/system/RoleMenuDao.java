package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleMenuDao extends BaseDao<RoleMenu> {
    RoleMenu findByRoleAndMenu(Role role, Menu menu);
    
    @Query("select r from RoleMenu r where r.menu = ?1")
    List<RoleMenu> findByMenu(Menu menu);
    
    @Query("select r from RoleMenu r where r.role = ?1")
    List<RoleMenu> findByRole(Role role);
}
