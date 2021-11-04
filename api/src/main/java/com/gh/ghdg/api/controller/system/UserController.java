package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.factory.PageFactory;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用户接口")
@RestController
@RequestMapping("sys/user")
public class UserController extends BaseController<User, UserDao, UserService> {
    @Autowired
    private UserService userService;
    
    /**
     * 根据用户名与昵称，进行模糊查询
     * @param username
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = "user:r")
    public Result list(@RequestParam(required = false) String username,
                       @RequestParam(required = false) String nickname){
        Page page = new PageFactory().defaultPage();
        if(StrUtil.isNotEmpty(nickname)){
            page.addFilter(SearchFilter.build("nickname", SearchFilter.Operator.LIKE, nickname));
        }
        if(StrUtil.isNotEmpty(username)){
            page.addFilter(SearchFilter.build("username", SearchFilter.Operator.LIKE, username));
        }
        page.addFilter(SearchFilter.build("status",SearchFilter.Operator.GT,0));
        page = userService.queryPage(page);
        
        return Result.suc(page.getRecords());
    }
    
    /**
     * save by user
     * @param t
     * @return
     * @throws Exception
     */
    @Override
    @PostMapping("save")
    @RequiresPermissions("user:cud")
    public Result save(@ModelAttribute("t") User t)throws Exception{
        return super.save(t);
    }
    
    /**
     *重置密码
     * @param t
     * @return
     */
    @PostMapping("resetPassword")
    @RequiresPermissions("user:cud")
    public Result resetPassword(@ModelAttribute("t") User t){
        service.resetPassword(t);
        return Result.suc("密码重置成功",t);
    }
    
    /**
     * 删除用户
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("delete/{id}")
    @RequiresPermissions("user:cud")
    @Override
    public Result delete(@ModelAttribute("t")User t)throws Exception{
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
    @PostMapping("setRoles")
    @RequiresPermissions("user:role:cud")
    public Result setRoles(@ModelAttribute("t")User t,String ids){
        service.saveRoles(t,ids);
        return Result.saveSuc();
    }
    
    /**
     * 删除分配角色
     * @param t
     * @param roleIds
     * @return
     */
    @PostMapping("deleteRoles/{id}/{roleIds}")
    @RequiresPermissions("user:role:cud")
    public Result deleteRoles(@ModelAttribute("t")User t, @PathVariable String roleIds){
        service.deleteRoles(t, roleIds);
        return Result.delSuc();
    }

}
