package com.gh.ghdg.sysMgr.bean.vo;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
        uvo.setId(u.getId());
        uvo.setUsername(u.getUsername());
        uvo.setNickname(u.getNickname());
        uvo.setGender(u.getGender());
        uvo.setEmail(u.getEmail());
        uvo.setPhone(u.getPhone());
        Date uCreatedDate = u.getCreatedDate();
        if(uCreatedDate!=null)
            uvo.setCreatedDate(uCreatedDate.getTime());
        uvo.setStatus(u.getStatus().getValue());
        Date uLastLoginDate = u.getLastLoginDate();
        if(uLastLoginDate!=null)
            uvo.setLastLoginDate(uLastLoginDate.getTime());
        uvo.setRemark(u.getRemark());
        
        List<String> rolenames = new ArrayList<>();
        for(UserRole ur:u.getUserRoles()){
            rolenames.add(ur.getRole().getRoleName());
        }
        uvo.setRolename(rolenames);
        return uvo;
    }
    
}
