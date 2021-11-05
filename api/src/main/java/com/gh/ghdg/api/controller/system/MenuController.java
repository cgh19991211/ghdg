package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.TreeController;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.sysMgr.bean.entities.system.Menu;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
import com.gh.ghdg.sysMgr.core.service.system.MenuService;
import com.gh.ghdg.common.utils.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单接口")
@RestController
@RequestMapping("sys/menu")
public class MenuController extends TreeController<Menu, MenuDao, MenuService> {
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @RequiresPermissions("menu:cud")
    public Result save(@ModelAttribute("t")Menu t)throws Exception{
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("delete/{id")
    @RequiresPermissions(("menu:cud"))
    public Result delete(@ModelAttribute("t")Menu t)throws Exception{
        return super.delete(t);
    }
    
    /**
     * 根据菜单名字模糊匹配菜单，传入的名字为空则返回全部菜单
     * @param name
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("menu:r")
    public Result list(String name){
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
    @PostMapping("move")
    @RequiresPermissions("menu:cud")
    public Result move(@ModelAttribute("t") Menu t, String overId, String position) throws Exception {
        return super.move(t, overId, position);
    }
    
    /**
     * 完整树
     * @return
     */
    @GetMapping("tree")
    @RequiresPermissions("menu:r")
    public List<Menu> tree() {
        return service.tree();
    }
    
    /**
     * 导航
     * @return
     */
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
    @PostMapping("assignRoles")
    @RequiresPermissions("menu:role:cud")
    public Result assignRoles(@ModelAttribute("t") Menu t,String roleIds, String modifiedPermissions) {
        service.saveRoles(t, roleIds, modifiedPermissions);
        return Result.saveSuc();
    }
    
    /**
     * 删除分配角色(回收)
     * @param t
     * @param roleIds
     * @return
     * @throws Exception
     */
    @GetMapping("recycleRoles")
    @RequiresPermissions("menu:role:cud")
    public Result recycleRoles(@ModelAttribute("t") Menu t,  String roleIds) {
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
    }
    
    /**
     * 角色分配的菜单
     * @param roleId
     * @return
     */
    @GetMapping("tree4Role")
    @RequiresPermissions("role:menu:r")
    public List<Menu> tree4Role(String roleId) {
        return service.tree4Role(roleId);
    }

    /**
     * 角色可分配的菜单
     * @param roleId
     * @return
     */
    @GetMapping("treeSelectable")
    @RequiresPermissions("role:menu:cud")
    public List<Menu> treeSelectable(String roleId) {
        return service.treeSelectable(roleId);
    }
    
}
