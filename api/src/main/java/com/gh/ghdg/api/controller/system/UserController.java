package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.constant.factory.PageFactory;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.dto.UserDto;
import com.gh.ghdg.sysMgr.bean.dto.UserDtoFactory;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@Api(tags = "用户接口")
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController<User, UserDao, UserService> {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    
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
        //按照最近登陆时间排序，登陆时间为null则排第一
        page.setSort(Sort.by(Sort.Direction.ASC,"lastLoginDate"));
        page = userService.queryPage(page);
        List<UserDto> users = new ArrayList<>();
        for(User u:(List<User>)page.getRecords()){
            users.add(UserDtoFactory.me().userVo(u));
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
    @RequiresPermissions(value = {PermissionCode.USER_EDIT,PermissionCode.USER_ADD})
    public Result userSave(@ModelAttribute("t") UserDto t)throws Exception{
        UserDtoFactory me = UserDtoFactory.me();
        String id = t.getId();
        if(StrUtil.isEmpty(id)){
            return Result.suc(userService.save(me.user(t,new User())));
        }else{
            Optional<User> byId = userDao.findById(id);
            if(byId.isPresent()){
                User user = byId.get();
                return Result.suc(userService.update(me.user(t,user)));
            }else{
                return Result.error(false,"用户不存在，id:"+id,null, Constants.FAILED);
            }
        }
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
    @RequiresPermissions(PermissionCode.USER_DELETE)
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
    @RequiresPermissions(PermissionCode.USER_ROLE_ADD)
    public Result userSetRoles(@RequestParam String userId,@RequestParam String roleIds){
        service.clearRoles(userId);
        service.saveRoles(userId,roleIds);
        return Result.saveSuc();
    }
    
    /**
     * 删除分配角色
     * @param t
     * @param roleIds
     * @return
     */
    @PostMapping("/deleteRoles")
    @RequiresPermissions(PermissionCode.USER_ROLE_ADD)
    public Result userDeleteRoles(@ModelAttribute("t")User t, @RequestParam String roleIds){
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
    }

}
