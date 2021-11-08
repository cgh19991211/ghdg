package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.TreeController;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.service.system.MenuService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags = "菜单接口")
//@Tag(name = "菜单接口")
@RestController
@RequestMapping("sys/menu")
public class MenuController extends TreeController<Menu, MenuDao, MenuService> {
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
//    @Operation(summary = "菜单新增")
//    @Parameter(name = "菜单入参")
    @PostMapping("save")
    @RequiresPermissions("menu:cud")
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
    @PostMapping("delete/{id")
    @RequiresPermissions(("menu:cud"))
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
    @GetMapping("list")
    @RequiresPermissions("menu:r")
    public Result menuList(String name){
        List menus = null;
        if(StrUtil.isEmptyIfStr(name))menus = service.queryAll();
        else menus = service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name));
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
    @PostMapping("move")
    @RequiresPermissions("menu:cud")
    public Result menuMove(@ModelAttribute("t") Menu t, String overId, String position) throws Exception {
        return super.move(t, overId, position);
    }
    
    /**
     * 完整树
     * @return
     */
//    @Operation(summary = "菜单树")
    @GetMapping("tree")
    @RequiresPermissions("menu:r")
    public List<Menu> menuTree() {
        return service.tree();
    }
    
    /**
     * 导航
     * @return
     */
//    @Operation(summary = "导航")
    @GetMapping("navigation")
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
    @PostMapping("assignRoles")
    @RequiresPermissions("menu:role:cud")
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
    @GetMapping("recycleRoles")
    @RequiresPermissions("menu:role:cud")
    public Result menuRecycleRoles(@ModelAttribute("t") Menu t,  String roleIds) {
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
    }
    
    /**
     * 角色分配的菜单
     * @param roleId
     * @return
     */
//    @Operation(summary = "角色分配的菜单树列表")
//    @Parameter(name = "角色id")
    @GetMapping("tree4Role")
    @RequiresPermissions("role:menu:r")
    public List<Menu> menuTree4Role(String roleId) {
        return service.tree4Role(roleId);
    }

    /**
     * 角色可分配的菜单
     * @param roleId
     * @return
     */
//    @Operation(summary = "角色可分配的菜单")
//    @Parameter(name = "角色id")
    @GetMapping("treeSelectable")
    @RequiresPermissions("role:menu:cud")
    public List<Menu> menuTreeSelectable(String roleId) {
        return service.treeSelectable(roleId);
    }
    
}
