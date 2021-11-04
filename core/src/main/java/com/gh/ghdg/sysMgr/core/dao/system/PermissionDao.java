package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.core.dao.DisplaySeqDao;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionDao extends DisplaySeqDao<Permission> {
    @Query("select p from Permission p where p.menu = ?1")
    List<Permission> findByMenu(Menu menu);
    
    @Query("select p from Permission p where p.permissionCode = ?1")
    Permission findByPermissionCode(String code);
}
