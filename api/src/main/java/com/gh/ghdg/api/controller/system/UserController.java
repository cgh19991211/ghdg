package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.constant.factory.PageFactory;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.bean.vo.UserVo;
import com.gh.ghdg.sysMgr.bean.vo.UserVoFactory;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import com.gh.ghdg.sysMgr.security.JwtUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//@Api(tags = "用户接口")
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController<User, UserDao, UserService> {
    @Autowired
    private UserService userService;
    
    /**
     * 根据用户名与昵称，进行模糊查询，分页
     * @param username
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(PermissionCode.USER)
    public Result userList(@RequestParam(required = false) String username,
                       @RequestParam(required = false) String nickname){
        Page page = new PageFactory().defaultPage();
        if(StrUtil.isNotEmpty(nickname)){
            page.addFilter(SearchFilter.build("nickname", SearchFilter.Operator.LIKE, nickname));
        }
        if(StrUtil.isNotEmpty(username)){
            page.addFilter(SearchFilter.build("username", SearchFilter.Operator.LIKE, username));
        }
//        page.addFilter(SearchFilter.build("status",SearchFilter.Operator.EQ,1));
        page = userService.queryPage (page);
        List<UserVo> users = new ArrayList<>();
        for(User u:(List<User>)page.getRecords()){
            users.add(UserVoFactory.me().userVo(u));
        }
        page.setRecords(users);
        return Result.suc(page);
    }
    
    /**
     * save by user
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @RequiresPermissions(PermissionCode.USER_EDIT)
    public Result userSave(@ModelAttribute("t") User t)throws Exception{
        return super.save(t);
    }
    
    @PostMapping("/modifyInfo")
    @RequiresPermissions(PermissionCode.USER_EDIT)
    public Result userModify(@ModelAttribute("t") User t) throws Exception {
        service.modifyInfo(t);
        return Result.suc("修改成功");
    }

    
    /**
     *重置密码
     * @param t
     * @return
     */
    @PostMapping("/resetPassword")
    @RequiresPermissions(PermissionCode.USER_EDIT)
    public Result userResetPassword(@RequestParam(required = true) String newPassword){
        service.resetPassword(newPassword);
        return Result.suc("密码重置成功");
    }
    
    /**
     * 删除用户
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions(PermissionCode.USER_EDIT)
    public Result userDelete(@ModelAttribute("t")User t)throws Exception{
        return super.delete(t);
    }
//
//    @GetMapping("list")
//    @RequiresPermissions("user:r")
//    public Result list(@ModelAttribute("t")User t){
//        Page page = new PageFactory().defaultPage();
//
//        page.addFilter();
//
//        return Result.suc(page);
//    }
    
    /**
     * 保存分配角色  -- 既在UserRole表里加记录
     * @param t
     * @param ids 角色ids
     * @return
     */
    @PostMapping("/setRoles")
    @RequiresPermissions(PermissionCode.USER_ROLE_EDIT)
    public Result userSetRoles(@ModelAttribute("t")User t,String ids){
        service.saveRoles(t,ids);
        return Result.saveSuc();
    }
    
    /**
     * 删除分配角色
     * @param t
     * @param roleIds
     * @return
     */
    @PostMapping("/deleteRoles/{id}/{roleIds}")
    @RequiresPermissions(PermissionCode.USER_ROLE_EDIT)
    public Result userDeleteRoles(@ModelAttribute("t")User t, @PathVariable String roleIds){
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
    }

}
