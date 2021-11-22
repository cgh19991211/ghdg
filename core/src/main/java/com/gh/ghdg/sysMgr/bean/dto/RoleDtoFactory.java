package com.gh.ghdg.sysMgr.bean.dto;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class RoleDtoFactory {
    public static RoleDtoFactory me(){return SpringContextUtil.getBean(RoleDtoFactory.class);
    }
    public RoleDto roleVo(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());//id
        roleDto.setName(role.getRoleName());//name
        roleDto.setNum(role.getDisplaySeq());
        roleDto.setTips(role.getRemark());
        roleDto.setCode(role.getRoleCode());
        roleDto.setLastModifiedDate(role.getLastModifiedDate());
        User createdBy = role.getCreatedBy();
        if(createdBy!=null){
            roleDto.setCreator(role.getCreatedBy().getUsername());
        }
        Role parent = role.getParent();//pid
        if(parent!=null){
            roleDto.setPid(parent.getId());
            roleDto.setpName(parent.getRoleName());
        }
        List<Role> children = role.getChildren();//children
        if(children!=null)
            roleDto.setChildren(getRVoList(children));
        return roleDto;
    }
    public List<RoleDto> getRVoList(List<Role> roles){
        ArrayList<RoleDto> roleDtos = new ArrayList<>();
        for(Role r:roles){
            roleDtos.add(roleVo(r));
        }
        return roleDtos;
    }
}
