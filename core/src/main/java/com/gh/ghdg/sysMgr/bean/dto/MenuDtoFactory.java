package com.gh.ghdg.sysMgr.bean.dto;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class MenuDtoFactory {
    public static MenuDtoFactory me(){return SpringContextUtil.getBean(MenuDtoFactory.class);
    }
    public MenuDto menuDto(Menu menu){
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getMenuName());
        Menu parent = menu.getParent();
        if(parent!=null)
            dto.setPid(parent.getId());
        List<Menu> children = menu.getChildren();
        if(children!=null){
            dto.setChildren(getDtoList(children));
        }
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
