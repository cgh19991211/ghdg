package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.TreeController;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.DisplaySeqEntity;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.dto.MenuDto;
import com.gh.ghdg.sysMgr.bean.dto.MenuDtoFactory;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuDao;
import com.gh.ghdg.sysMgr.core.service.system.MenuService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@Api(tags = "菜单接口")
//@Tag(name = "菜单接口")
@RestController
@RequestMapping("sys/menu")
public class MenuController extends TreeController<Menu, MenuDao, MenuService> {
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoleMenuDao roleMenuDao;
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @RequiresPermissions(value = {PermissionCode.MENU_ADD,PermissionCode.MENU_EDIT})
    public Result menuSave(@ModelAttribute("t")Menu t)throws Exception{
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @RequiresPermissions(PermissionCode.MENU_DELETE)
    public Result menuDelete(@ModelAttribute("t")Menu t)throws Exception{
        return super.delete(t);
    }
    
    /**
     * 根据菜单名字模糊匹配菜单，传入的名字为空则返回全部菜单
     * @param name
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions(PermissionCode.MENU)
    public Result menuList(String name){
        List<Menu> menus;
        SearchFilter sf = SearchFilter.build("parent",SearchFilter.Operator.ISNULL);
        if(StrUtil.isEmptyIfStr(name))menus = new ArrayList<>(service.queryAll(sf));
        else menus = new ArrayList<>(service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name)));
        menus.sort(Comparator.comparingInt(DisplaySeqEntity::getDisplaySeq));
        return Result.suc(menus);
    }
    
    /**
     * 完整树--权限新增或修改需要获取
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions(PermissionCode.MENU)
    public Result menuTree() {
        return Result.suc(service.tree());
    }
    
    /**
     * 菜单树，分配给了参数的角色的菜单要加到checked里
     * 每个菜单要添加权限id列表--》Menu->RoleMenu->Permission
     * @param roleId
     * @return
     */
    @GetMapping("/menuTree4Role")
    @RequiresPermissions(PermissionCode.ROLE_MENU)
    public Result menuTree4Role(String roleId) {
//        return service.tree4Role(roleId);
        //map:存放checkedIds,treeData(menu)  menu:{checked,children,id,name,pid,permissions}
        Map<String,List> map = new HashMap<>();
        List<String> checkedIds = new ArrayList<>();
        List<MenuDto> treeData = new ArrayList<>();
    
        //get role by roleId
        Role role = roleDao.getById(roleId);
        //get assigned menus'id
        Set<String> assigned = new HashSet<>();
        List<RoleMenu> roleMenus = roleMenuDao.findByRole(role);
        for(RoleMenu rm:roleMenus){
            assigned.add(rm.getMenu().getId());
        }
        
        //all root menu
        List<Menu> roots = service.tree();
        for(Menu root:roots){
            recurRoot(treeData,checkedIds,assigned,root);
        }
        map.put("treeData",treeData);
        map.put("checkedIds",checkedIds);
        return Result.suc(map);
    }
    
    /**
     * 递归菜单获取所有已经分配给了该角色的菜单id
     * @param treeData
     * @param checkedIds
     * @param assigned
     * @param menu
     */
    private void recurRoot(List<MenuDto> treeData,List<String> checkedIds,Set<String> assigned,Menu menu){
        MenuDto dto = MenuDtoFactory.me().menuDto(menu);
        if(assigned.contains(menu.getId())){
            checkedIds.add(menu.getId());
        }
        if(menu.getParent()==null)
            treeData.add(dto);
        //递归
        List<Menu> children = menu.getChildren();
        if(children!=null&&children.size()>0){
            for(Menu m:children){
                recurRoot(treeData,checkedIds,assigned,m);
            }
        }
    }

}
