package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserMenu;
import com.gh.ghdg.sysMgr.core.dao.system.UserMenuDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserMenuService extends BaseService<UserMenu, UserMenuDao> {
    /**
     * 保存
     * @param user
     * @param menu
     */
    @Transactional
    public void save(User user, Menu menu) {
        UserMenu t = dao.findByUserAndMenu(user, menu);
        if(null == t) {
            t = new UserMenu();
            t.setUser(user);
            t.setMenu(menu);
            dao.save(t);
        }
    }
    
    /**
     * 删除
     * @param user
     * @param menu
     */
    @Transactional
    public void delete(User user, Menu menu) {
        UserMenu t = dao.findByUserAndMenu(user, menu);
        if(null != t) {
            dao.delete(t);
        }
    }
}
