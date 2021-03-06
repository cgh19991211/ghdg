package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRoleDao extends BaseDao<UserRole> {
    @Query("select u from UserRole u where u.user = ?1 and u.role = ?2")
    UserRole findByUserAndRole(User user, Role role);
    
    @Query("select u from UserRole u where u.user = ?1")
    List<UserRole> findByUser(User user);
    
    @Query("select u from UserRole u where u.role = ?1")
    List<UserRole> findByRole(Role role);
    
    @Modifying
    @Query("delete from UserRole u where u.user.id = ?1")
    @Transactional
    void deleteAllByUserId(String uid);
}
