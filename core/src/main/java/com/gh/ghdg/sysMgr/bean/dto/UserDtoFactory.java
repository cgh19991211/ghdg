package com.gh.ghdg.sysMgr.bean.dto;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class UserDtoFactory {
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserDao userDao;
    
    public static UserDtoFactory me() {
        return SpringContextUtil.getBean(UserDtoFactory.class);
    }
    
    public UserDto userVo(User u){
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setNickname(u.getNickname());
        dto.setGender(u.getGender());
        dto.setEmail(u.getEmail());
        dto.setPhone(u.getPhone());
        Date uCreatedDate = u.getCreatedDate();
        if(uCreatedDate!=null)
            dto.setCreatedDate(uCreatedDate.getTime());
        dto.setStatus(u.getStatus().getValue());
        Date uLastLoginDate = u.getLastLoginDate();
        if(uLastLoginDate!=null)
            dto.setLastLoginDate(uLastLoginDate.getTime());
        dto.setRemark(u.getRemark());
        Optional<User> byId = userDao.findById(u.getId());
        if(!byId.isPresent()){
            throw new MyException("用户:"+u.getId()+"不存在");
        }
        User user = byId.get();
        List<UserRole> userRoles = user.getUserRoles();
        List<String> rolenames = new ArrayList<>();
        for(UserRole ur:userRoles){
            rolenames.add(ur.getRole().getRoleName());
        }
        dto.setRolename(rolenames);
        return dto;
    }
    public User user(UserDto dto,User u){
        if(dto==null){
            return null;
        }else{
            String username = dto.getUsername();
            if(StrUtil.isNotEmpty(username))
                u.setUsername(username);
    
            String nickname = dto.getNickname();
            if(StrUtil.isNotEmpty(nickname))
                u.setNickname(nickname);
    
            String gender = dto.getGender();
            if(StrUtil.isNotEmpty(gender))
                u.setGender(gender);
    
            String email = dto.getEmail();
            if(StrUtil.isNotEmpty(email))
                u.setEmail(email);
    
            String phone = dto.getPhone();
            if(StrUtil.isNotEmpty(phone))
                u.setPhone(phone);
    
            Integer status = dto.getStatus();
            if(status!=null)
                if(status==1)
                    u.setStatus(Status.生效);
                else
                    u.setStatus(Status.失效);
    
            String remark = dto.getRemark();
            if(StrUtil.isNotEmpty(remark))
                u.setRemark(remark);
            
            return u;
        }
    }
}
