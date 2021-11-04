package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.service.system.RoleService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController<Role, RoleDao, RoleService> {
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @RequiresPermissions("role:cud")
    public Result save(@ModelAttribute("t") Role t) throws Exception {
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @GetMapping("delete/{id}")
    @RequiresPermissions("role:cud")
    public Result delete(@ModelAttribute("t") Role t) throws Exception {
        return super.delete(t);
    }
    
    /**
     * 根据传入的角色名查询角色，如果角色名为空，则返回所有角色
     * @param name
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions(("role:r"))
    public Result list(String name){
        List roles = null;
        if(StrUtil.isEmpty(name)){
            roles = service.queryAll();
        }else{
            roles = service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name));
        }
        return Result.suc(roles);
    }
    
//    /** 放到UserController中来做
//     * 为用户分配角色
//     * @param t
//     * @param userIds
//     * @return
//     */
//    @PostMapping("assignUsers/{userIds}")
//    @RequiresPermissions("user:role:cud")
//    public Result assignUsers(@ModelAttribute("t") Role t,@PathVariable String userIds){
//        service.assignUsers(t,userIds);
//        return Result.saveSuc();
//    }
//
//    /**
//     * 回收角色
//     * @param t
//     * @param userIds
//     * @return
//     */
//    @GetMapping("recycleUsers/{id}/{userIds}")
//    @RequiresPermissions(("role:user:cud"))
//    public Result recycleUsers(@ModelAttribute("t")Role t,@PathVariable String userIds){
//        service.recycleUsers(t,userIds);
//        return Result.delSuc();
//    }
    
    
    
    /**
     * 完整树
     * @return
     */
    @GetMapping("tree")
    @RequiresPermissions("role:r")
    public List<Role> tree(@ModelAttribute("t") Role t) {
        return service.tree(t);
    }
    
    /**
     * 用户角色树
     * @param userId
     * @return
     */
    @GetMapping("tree4User")
    @RequiresPermissions("user:role:r")
    public List<Role> tree4User(String userId){
        return service.tree4User(userId);
    }
    
    /**
     * 用户可选角色树
     * @param userId
     * @return
     */
    @GetMapping("selectableTree4User")
    @RequiresPermissions("user:role:r")
    public List<Role> selectableTree4User(String userId){
        return service.selectableTree4User(userId);
    }
    
    /**
     * 菜单分配的角色
     * @param
     * @param menuId
     * @return
     */
    @GetMapping("tree4Menu")
    @RequiresPermissions("menu:role:r")
    public List<Role> tree4Menu( String menuId) {
        return service.tree4Menu( menuId);
    }
    
    /**
     * 菜单可分配的角色
     * @param
     * @param menuId
     * @return
     */
    @GetMapping("treeSelectable4Menu")
    @RequiresPermissions("menu:role:cud")
    public List<Role> treeSelectable4Menu(String menuId) {
        return service.selectableTree4Menu( menuId);
    }
}
