package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {
    
    @Query("select s from sys_role s where s.roleName = ?1")
    List findByRoleName(String roleName);
}
