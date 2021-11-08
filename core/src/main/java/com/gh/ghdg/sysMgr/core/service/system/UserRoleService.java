package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.core.dao.system.UserRoleDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService extends BaseService<UserRole, UserRoleDao> {
    
    @Transactional
    public void save(User u, Role r) {
        UserRole t = dao.findByUserAndRole(u, r);
        if(null == t) {
            t = new UserRole();
            t.setUser(u);
            t.setRole(r);
            dao.save(t);
        }
    }
    
    public List<Role> list(User t){
        List<UserRole> byUser = dao.findByUser(t);
        List<Role> res = new ArrayList<>();
        for(UserRole ur:byUser){
            res.add(ur.getRole());
        }
        return res;
    }
    public List<User> list(Role t){
        List<UserRole> byUser = dao.findByRole(t);
        List<User> res = new ArrayList<>();
        for(UserRole ur:byUser){
            res.add(ur.getUser());
        }
        return res;
    }

}
