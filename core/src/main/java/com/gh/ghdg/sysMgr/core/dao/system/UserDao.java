package com.gh.ghdg.sysMgr.core.dao.system;

import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.common.BaseDao;

public interface UserDao extends BaseDao<User> {
    User findByUsername(String username);
}
