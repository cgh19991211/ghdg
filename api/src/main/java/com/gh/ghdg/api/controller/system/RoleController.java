package com.gh.ghdg.api.controller.system;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.bean.vo.RoleVo;
import com.gh.ghdg.sysMgr.bean.vo.RoleVoFactory;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.RoleService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@Api(tags = "角色接口")
@RestController
@RequestMapping("sys/role")
public class RoleController extends BaseController<Role, RoleDao, RoleService> {
    
    @Autowired
    private UserDao userDao;
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @RequiresPermissions(PermissionCode.ROLE_EDIT)
    public Result roleSave(@ModelAttribute("t") Role t) throws Exception {
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @GetMapping("delete/{id}")
    @RequiresPermissions(PermissionCode.ROLE_EDIT)
    public Result roleDelete(@ModelAttribute("t") Role t) throws Exception {
        return super.delete(t);
    }
    
    /**
     * 根据传入的角色名查询角色，如果角色名为空，则返回所有角色
     * @param name
     * @return
     */
//    @GetMapping("list")
//    @RequiresPermissions(PermissionCode.ROLE)
//    public Result roleList(String name){
//        List roles = null;
//        if(StrUtil.isEmpty(name)){
//            roles = service.queryAll();
//        }else{
//            roles = service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name));
//        }
//        return Result.suc(roles);
//    }
    
    /**
     * 角色根树列表
     * @param userId
     * @return 根角色Vo列表
     */
    @PostMapping("/roleList")
    @RequiresPermissions(PermissionCode.ROLE)
    public Result roleList(@RequestParam String  userId){
        User user = userDao.getById(userId);
        Set<String> assigned = new HashSet<>();
        List<UserRole> userRoles = user.getUserRoles();
        for(UserRole ur:userRoles){
            assigned.add(ur.getRole().getId());
        }
        
        List<Role> roleList = service.tree();
        List<RoleVo> roleVoList = new ArrayList<>();
        RoleVoFactory me = RoleVoFactory.me();
        for(Role r:roleList){
            RoleVo roleVo = me.roleVo(r);
            if(assigned.contains(r.getId())){
                roleVo.setChecked(true);
            }
            roleVoList.add(roleVo);
        }
        return Result.suc(roleVoList);
    }
    
    /**
     * 完整根树列表
     *  id,name,pid,children
     * @return
     */
    @GetMapping("/tree")
    @RequiresPermissions((PermissionCode.ROLE))
    public Result roleTree() {
        List<Role> roleList = service.tree();
        List<RoleVo> roleVos = new ArrayList<>();
        RoleVoFactory roleVoFactory = RoleVoFactory.me();
        for(Role r:roleList){
            roleVos.add(roleVoFactory.roleVo(r));
        }
        return Result.suc(roleVos);
    }
    
    /**
     * 用户角色树
     * @param userId
     * @return
     */
    @GetMapping("/tree4User")
    @RequiresPermissions(PermissionCode.USER_ROLE)
    public Result roleTree4User(@RequestParam(required = true) String userId){
        return Result.suc(service.tree4User(userId));
    }
    
    /**
     * 用户可选角色树
     * @param userId
     * @return
     */
    @GetMapping("/selectableTree4User")
    @RequiresPermissions(PermissionCode.USER_ROLE)
    public Result roleSelectableTree4User(String userId){
        return Result.suc(service.selectableTree4User(userId));
    }
    
    /**
     * 菜单分配的角色
     * @param
     * @param menuId
     * @return
     */
    @GetMapping("tree4Menu")
    @RequiresPermissions(PermissionCode.ROLE_MENU)
    public List<Role> roleTree4Menu( String menuId) {
        return service.tree4Menu( menuId);
    }
    
    /**
     * 菜单可分配的角色
     * @param
     * @param menuId
     * @return
     */
    @GetMapping("treeSelectable4Menu")
    @RequiresPermissions(PermissionCode.ROLE_MENU)
    public List<Role> roleTreeSelectable4Menu(String menuId) {
        return service.selectableTree4Menu( menuId);
    }
}
