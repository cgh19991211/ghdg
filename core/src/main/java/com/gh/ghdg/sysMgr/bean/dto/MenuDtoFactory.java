package com.gh.ghdg.sysMgr.bean.dto;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenu;
import com.gh.ghdg.sysMgr.bean.entities.system.RoleMenuPermission;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuPermissionDao;
import com.gh.ghdg.sysMgr.core.service.system.RoleMenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class MenuDtoFactory {
    
    @Autowired
    private RoleMenuDao roleMenuDao;
    
    @Autowired
    private RoleMenuPermissionDao roleMenuPermissionDao;
    
    public static MenuDtoFactory me(){return SpringContextUtil.getBean(MenuDtoFactory.class);
    }
    public MenuDto menuDto(Menu menu){
        MenuDto dto = new MenuDto();
        //base fields
        dto.setId(menu.getId());
        dto.setName(menu.getMenuName());
        //pid
        Menu parent = menu.getParent();
        if(parent!=null)
            dto.setPid(parent.getId());
        //children
        List<Menu> children = menu.getChildren();
        if(children!=null){
            dto.setChildren(getDtoList(children));
        }
        //permissions
//        List<RoleMenu> roleMenus = roleMenuDao.findByMenu(menu);
        Permission permission = menu.getPermission();
        if(permission!=null)
            dto.setPermissionId(permission.getId());
        return dto;
    }
    public List<MenuDto> getDtoList(List<Menu> menus){
        List<MenuDto> menuDtos = new ArrayList<>();
        for(Menu menu:menus){
            menuDtos.add(menuDto(menu));
        }
        return menuDtos;
    }
}
