package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.common.BaseDao;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserMenu;
import org.springframework.data.jpa.repository.Query;

public interface UserMenuDao extends BaseDao<UserMenu> {
    @Query("select u from UserMenu u where u.user = ?1 and u.menu = ?2")
    UserMenu findByUserAndMenu(User user, Menu menu);
//    @Query("select u from UserMenu u where u.user = ?1")
//    List<UserMenu> findByUser(User user);

}
