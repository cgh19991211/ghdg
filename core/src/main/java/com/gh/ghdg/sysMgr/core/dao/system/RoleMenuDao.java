package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleMenuDao extends BaseDao<RoleMenu> {
    @Query("select r from RoleMenu r where r.role = ?1 and r.menu = ?2")
    RoleMenu findByRoleAndMenu(Role role, Menu menu);
    
    @Query("select (count(r) > 0) from RoleMenu r where r.role = ?1 and r.menu = ?2")
    Boolean existsByRoleAndMenu(Role role, Menu menu);
    
    @Query("select r from RoleMenu r where r.menu = ?1")
    List<RoleMenu> findByMenu(Menu menu);
    
    @Query("select r from RoleMenu r where r.role = ?1")
    List<RoleMenu> findByRole(Role role);
}
