package com.gh.ghdg.sysMgr.bean.vo;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class UserVoFactory {
    
    public static UserVoFactory me() {
        return SpringContextUtil.getBean(UserVoFactory.class);
    }
    
    public UserVo userVo(User u){
        UserVo uvo = new UserVo();
        uvo.setUsername(u.getUsername());
        uvo.setNickname(u.getNickname());
        uvo.setGender(u.getGender());
        uvo.setEmail(u.getEmail());
        uvo.setPhone(u.getPhone());
        uvo.setCreatedDate(u.getCreatedDate());
        uvo.setStatus(u.getStatus().getValue());
        uvo.setLastLoginDate(u.getLastLoginDate());
        
        List<String> rolenames = new ArrayList<>();
        for(UserRole ur:u.getUserRoles()){
            rolenames.add(ur.getRole().getRoleName());
        }
        uvo.setRolename(rolenames);
        return uvo;
    }
}
