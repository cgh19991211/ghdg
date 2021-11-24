package com.gh.ghdg.sysMgr.bean.dto;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class PermissionDtoFactory {
    public static PermissionDtoFactory me(){
        return SpringContextUtil.getBean(PermissionDtoFactory.class);
    }
    public PermissionDto permissionDto(Permission permission){
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setName(permission.getPermissionName());
        permissionDto.setCode(permission.getPermissionCode());
        permissionDto.setUrl(permission.getUrl());
        Permission parent = permission.getParent();
        if(parent!=null){
            permissionDto.setPid(parent.getId());
            permissionDto.setpName(parent.getPermissionName());
        }
        Menu menu = permission.getMenu();
        if(menu!=null){
            permissionDto.setMenuId(menu.getId());
            permissionDto.setMenuName(menu.getMenuName());
        }
        permissionDto.setDisplaySeq(permission.getDisplaySeq());
        permissionDto.setRemark(permission.getRemark());
        //TODO:get children
        List<Permission> children = permission.getChildren();
        if(children!=null)
            permissionDto.setChildren(getPermissionDtoList(children));
        return permissionDto;
    }
    public List<PermissionDto> getPermissionDtoList(List<Permission> permissions){
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for(Permission p:permissions){
            permissionDtos.add(permissionDto(p));
        }
        return permissionDtos;
    }
}
