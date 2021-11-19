package com.gh.ghdg.sysMgr.bean.vo;

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
public class RoleVoFactory {
    public static RoleVoFactory me(){return SpringContextUtil.getBean(RoleVoFactory.class);
    }
    public RoleVo roleVo(Role role){
        RoleVo roleVo = new RoleVo();
        roleVo.setId(role.getId());//id
        roleVo.setName(role.getRoleName());//name
        roleVo.setNum(role.getDisplaySeq());
        roleVo.setTips(role.getRemark());
        roleVo.setCode(role.getRoleCode());
        roleVo.setLastModifiedDate(role.getLastModifiedDate());
        User createdBy = role.getCreatedBy();
        if(createdBy!=null){
            roleVo.setCreator(role.getCreatedBy().getUsername());
        }
        Role parent = role.getParent();//pid
        if(parent!=null){
            roleVo.setPid(parent.getId());
            roleVo.setpName(parent.getRoleName());
        }
        List<Role> children = role.getChildren();//children
        if(children!=null)
            roleVo.setChildren(getRVoList(children));
        return roleVo;
    }
    public List<RoleVo> getRVoList(List<Role> roles){
        ArrayList<RoleVo> roleVos = new ArrayList<>();
        for(Role r:roles){
            roleVos.add(roleVo(r));
        }
        return roleVos;
    }
}
