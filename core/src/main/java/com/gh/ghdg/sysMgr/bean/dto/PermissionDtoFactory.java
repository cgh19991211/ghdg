package com.gh.ghdg.sysMgr.bean.dto;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.commonVo.DynamicSpecifications;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class PermissionDtoFactory {
    
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
    
    public static PermissionDtoFactory me(){
        return SpringContextUtil.getBean(PermissionDtoFactory.class);
    }
    public PermissionDto permissionDto(Permission permission){
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setName(permission.getPermissionName());
        permissionDto.setCode(permission.getPermissionCode());
        permissionDto.setUrl(permission.getUrl());
        permissionDto.setExpanded(permission.getExpanded());
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
    public Permission permission(PermissionDto dto){
        Permission permission = new Permission();
        if(StrUtil.isNotEmpty(dto.getId()))
            permission.setId(dto.getId());
        permission.setPermissionName(dto.getName());
        permission.setPermissionCode(dto.getCode());
        permission.setUrl(dto.getUrl());
        if(StrUtil.isNotEmpty(dto.getPid())){
//            SearchFilter id = SearchFilter.build("id", SearchFilter.Operator.EQ, dto.getId());
//            ArrayList<SearchFilter> searchFilters = new ArrayList<>();
//            searchFilters.add(id);
//            Optional<Permission> one = permissionDao.findOne(DynamicSpecifications.bySearchFilter(searchFilters, Permission.class));
//            if(one.isPresent())
//                permission.setParent(one.get());
            Permission byId = permissionDao.getById(dto.getPid());
            if(byId!=null)
                permission.setParent(byId);
        }
        if(StrUtil.isNotEmpty(dto.getMenuId())){
            Menu menu = menuDao.getById(dto.getMenuId());
            if(menu!=null){
                permission.setMenu(menu);
            }
        }
        permission.setRemark(dto.getRemark());
        return permission;
    }
}
