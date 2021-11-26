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
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuDao;
import com.gh.ghdg.sysMgr.core.dao.system.RoleMenuPermissionDao;
import com.gh.ghdg.sysMgr.core.service.system.MenuService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.tags.Tag;
import com.gh.ghdg.sysMgr.core.service.system.RoleMenuPermissionService;
import com.gh.ghdg.sysMgr.core.service.system.RoleMenuService;
import com.gh.ghdg.sysMgr.core.service.system.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

//@Api(tags = "菜单接口")
//@Tag(name = "菜单接口")
@RestController
@RequestMapping("sys/menu")
public class MenuController extends TreeController<Menu, MenuDao, MenuService> {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, true));
    }
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoleMenuDao roleMenuDao;
    
    @Autowired
    private RoleMenuPermissionDao roleMenuPermissionDao;
    
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
//    @Operation(summary = "菜单新增")
//    @Parameter(name = "菜单入参")
    @PostMapping("/save")
    @RequiresPermissions(PermissionCode.MENU_EDIT)
    public Result menuSave(@ModelAttribute("t")Menu t)throws Exception{
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     * @throws Exception
     */
//    @Operation(summary = "菜单删除")
//    @Parameter(name = "菜单入参")
    @PostMapping("/delete")
    @RequiresPermissions(PermissionCode.MENU_EDIT)
    public Result menuDelete(@ModelAttribute("t")Menu t)throws Exception{
        return super.delete(t);
    }
    
    /**
     * 根据菜单名字模糊匹配菜单，传入的名字为空则返回全部菜单
     * @param name
     * @return
     */
//    @Operation(summary = "菜单列表")
//    @Parameter(name = "菜单名字")
    @GetMapping("/list")
    @RequiresPermissions(PermissionCode.MENU)
    public Result menuList(String name){
        
        List<Menu> menus = null;
        SearchFilter sf = SearchFilter.build("parent",SearchFilter.Operator.ISNULL);
        if(StrUtil.isEmptyIfStr(name))menus = new ArrayList<>(service.queryAll(sf));
        else menus = new ArrayList<>(service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name)));
        menus.sort(Comparator.comparingInt(DisplaySeqEntity::getDisplaySeq));
        return Result.suc(menus);
    }
    
    /**
     * 移动
     * @param t
     * @param overId
     * @return
     * @throws Exception
     */
//    @Operation(summary = "菜单移动")
//    @Parameters(value = {@Parameter(name = "菜单入参"),@Parameter(name = "移动到哪个位置"),@Parameter(name = "前后")})
    @PostMapping("/move")
    @RequiresPermissions(PermissionCode.MENU_EDIT)
    public Result menuMove(@ModelAttribute("t") Menu t, String overId, String position) throws Exception {
        return super.move(t, overId, position);
    }
    
    /**
     * 完整树--权限新增或修改需要获取
     * @return
     */
//    @Operation(summary = "菜单树")
    @GetMapping("/tree")
    @RequiresPermissions(PermissionCode.MENU)
    public Result menuTree() {
        return Result.suc(service.tree());
    }
    
    /**
     * 导航
     * @return
     */
//    @Operation(summary = "导航")
    @GetMapping("/navigation")
    public List<Menu> navigation() {
        return service.navigation();
    }

    /**
     * 保存分配角色(分配)
     * @param t
     * @param roleIds
     * @param modifiedPermissions 权限id 用","分隔成组 组内用"|"分隔每一个权限 传进来的都是要分配的
     * @return
     */
//    @Operation(summary = "为菜单分配角色")
//    @Parameters(value = {@Parameter(name = "菜单入参"),@Parameter(name = "角色id"),@Parameter(name = "权限代码")})
    @PostMapping("/assignRoles")
    @RequiresPermissions(PermissionCode.ROLE_MENU_EDIT)//role.menu.edit
    public Result menuAssignRoles(@ModelAttribute("t") Menu t,String roleIds, String modifiedPermissions) {
        service.saveRoles(t, roleIds, modifiedPermissions);
        return Result.saveSuc();
    }
    
    /**
     * 删除分配角色(回收)
     * @param t
     * @param
     * @return
     * @throws Exception
     */
//    @Operation(summary = "回收菜单角色")
//    @Parameters(value = {@Parameter(name = "菜单入参"),@Parameter(name = "角色id")})
    @GetMapping("/recycleRoles")
    @RequiresPermissions(PermissionCode.ROLE_MENU_EDIT)
    public Result menuRecycleRoles(@ModelAttribute("t") Menu t,  String roleIds) {
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
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
        List<Menu> tree = service.tree();
        for(Menu m:tree){
            recurRoot(treeData,checkedIds,assigned,m);
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
    

    /**
     * 角色可分配的菜单
     * @param roleId
     * @return
     */
//    @Operation(summary = "角色可分配的菜单")
//    @Parameter(name = "角色id")
    @GetMapping("/treeSelectable")
    @RequiresPermissions(PermissionCode.ROLE_MENU_EDIT)
    public List<Menu> menuTreeSelectable(String roleId) {
        return service.treeSelectable(roleId);
    }
    

}
