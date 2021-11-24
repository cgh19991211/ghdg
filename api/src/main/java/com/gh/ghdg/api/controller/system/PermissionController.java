package com.gh.ghdg.api.controller.system;
import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.DisplaySeqController;
import com.gh.ghdg.common.commonVo.Page;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.constant.factory.PageFactory;
import com.gh.ghdg.sysMgr.bean.dto.PermissionDto;
import com.gh.ghdg.sysMgr.bean.dto.PermissionDtoFactory;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.core.dao.system.PermissionDao;
import com.gh.ghdg.sysMgr.core.service.system.PermissionService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Api(tags = "权限接口")
@RestController
@RequestMapping("sys/permission")
public class PermissionController extends DisplaySeqController<Permission,PermissionDao, PermissionService> {
    @Autowired
    private PermissionDao permissionDao;
    /**
     * 新增
     * @param t
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionSave(@ModelAttribute("t") PermissionDto t) throws Exception {
        return super.save(PermissionDtoFactory.me().permission(t));
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
    @GetMapping("delete")
    @RequiresPermissions(PermissionCode.PERMISSION_EDIT)
    public Result permissionDelete(@RequestParam String id) throws Exception {
        Permission  byId = permissionDao.getById(id);
        
        return super.delete(byId);
    }
    
    
    /**
     * 列表
     * @param t
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions(PermissionCode.PERMISSION)
    public Result permissionList(String name) throws Exception {
//        return Result.suc(service.list(t));
        Page page = new PageFactory().defaultPage();
        page.setSort(Sort.by(Sort.Direction.ASC, "displaySeq"));
        if(StrUtil.isNotEmpty(name)){
            page.addFilter(SearchFilter.build("permissionName", SearchFilter.Operator.LIKE,name));
        }else{
            page.addFilter(SearchFilter.build("parent",SearchFilter.Operator.ISNULL));
        }
        page = service.queryPage(page);
        PermissionDtoFactory me = PermissionDtoFactory.me();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for(Permission p:(List<Permission>) page.getRecords()){
            permissionDtoList.add(me.permissionDto(p));
        }
        page.setRecords(permissionDtoList);
        return Result.suc(page);
    }


}
