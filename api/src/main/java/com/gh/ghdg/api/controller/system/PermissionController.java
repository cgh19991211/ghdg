package com.gh.ghdg.api.controller.system;
import com.gh.ghdg.api.controller.DisplaySeqController;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.core.dao.system.PermissionDao;
import com.gh.ghdg.sysMgr.core.service.system.PermissionService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags = "权限接口")
@RestController
@RequestMapping("sys/permission")
public class PermissionController extends DisplaySeqController<Permission,PermissionDao, PermissionService> {
    
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionSave(@ModelAttribute("t") Permission t) throws Exception {
        return super.save(t);
    }
    
    @PostMapping("update")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionUpdate(@ModelAttribute("t")Permission t) throws Exception{
        return Result.saveSuc(super.save(t));
    }
    
    /**
     * 移动
     * @param t
     * @param overId
     * @return
     * @throws Exception
     */
    @PostMapping("move")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionMove(@ModelAttribute("t") Permission t, String overId, String position) throws Exception {
        return super.move(t, overId, position);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @GetMapping("delete/{id}")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionDelete(@ModelAttribute("t") Permission t) throws Exception {
        return super.delete(t);
    }
    
    
    /**
     * 列表
     * @param t
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions(PermissionCode.PERMISSION)
    public List<Permission> permissionList(Permission t) throws Exception {
        return service.list(t);
    }


}
