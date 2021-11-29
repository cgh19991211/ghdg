package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.constant.factory.PageFactory;
import com.gh.ghdg.sysMgr.bean.dto.MenuDto;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.bean.dto.RoleDto;
import com.gh.ghdg.sysMgr.bean.dto.RoleDtoFactory;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.RoleService;
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
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @RequiresPermissions(value = {PermissionCode.ROLE_EDIT,PermissionCode.ROLE_ADD})
    public Result roleSave(@ModelAttribute("t") Role t) throws Exception {
        if(StrUtil.isEmpty(t.getId())){
            return Result.suc(roleService.save(t));
        }else{
            return Result.suc(roleService.update(t));
        }
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @GetMapping("delete")
    @RequiresPermissions(PermissionCode.ROLE_DELETE)
    public Result roleDelete(@ModelAttribute("t") Role t) throws Exception {
        return super.delete(t);
    }
    
    /**
     * 根据传入的角色名查询角色，如果角色名为空，则返回所有角色
     * @param name
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions(PermissionCode.ROLE)
    public Result roleShowList(String name){
//        List<Role> roles = null;
        Page page = new PageFactory().defaultPage();
        if(StrUtil.isEmpty(name)){
//            roles = service.queryAll();
        }else{
//            roles = service.queryAll(SearchFilter.build("name",SearchFilter.Operator.LIKE,name));
            page.addFilter(SearchFilter.build("roleName",SearchFilter.Operator.LIKE,name));
        }
        page = roleService.queryPage(page);
        RoleDtoFactory me = RoleDtoFactory.me();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for(Role r: (List<Role>)page.getRecords()){
            roleDtoList.add(me.roleVo(r));
        }
        page.setRecords(roleDtoList);
        return Result.suc(page);
    }
    
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
    
        List<String> checkedIds = new ArrayList<>();
        List<RoleDto> treeData = new ArrayList<>();
        List<Role> root = service.tree();
        Map<String,List> map = new HashMap<>();
        for(Role r:root){
            recurRoot(treeData,checkedIds,assigned,r);
        }
        map.put("treeData",treeData);
        map.put("checkedIds",checkedIds);
        return Result.suc(map);
    }
    
    private void recurRoot(List<RoleDto> treeData,List<String> checkedIds,Set<String> assigned,Role role){
        RoleDto dto = RoleDtoFactory.me().roleVo(role);
        if(assigned.contains(role.getId())){
            checkedIds.add(role.getId());
        }
        if(role.getParent()==null)
            treeData.add(dto);
        List<Role> children = role.getChildren();
        if(children!=null&&children.size()>0){
            for(Role r:children){
                recurRoot(treeData,checkedIds,assigned,r);
            }
        }
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
        List<RoleDto> roleDtos = new ArrayList<>();
        RoleDtoFactory roleDtoFactory = RoleDtoFactory.me();
        for(Role r:roleList){
            roleDtos.add(roleDtoFactory.roleVo(r));
        }
        return Result.suc(roleDtos);
    }
    
    /**
     * 角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("setMenusAndPermissions")
    @RequiresPermissions(PermissionCode.ROLE_MENU_ADD)
    public Result assignMenusAndPermissions(@RequestParam String roleId,
                                            @RequestParam String permissionIds){
        service.setMenusAndPermissions(roleId,permissionIds);
        return Result.saveSuc();
    }

}
