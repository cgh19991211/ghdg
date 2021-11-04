package com.gh.ghdg.api.controller.system;

import com.gh.ghdg.api.controller.TreeController;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.entities.system.Module;
import com.gh.ghdg.sysMgr.core.dao.system.ModuleDao;
import com.gh.ghdg.sysMgr.core.service.system.ModuleService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "模块接口")
@RestController
@RequestMapping("sys/module")
public class ModuleController extends TreeController<Module, ModuleDao, ModuleService> {
    
    @PostMapping("save")
//    @RequiresPermissions("module:cud")
    public Result save(@ModelAttribute("t")Module t)throws Exception{
        return super.save(t);
    }
    
    /**
     * 移动
     * @param t
     * @param overId
     * @return
     * @throws Exception
     */
    @PostMapping("move")
    @RequiresPermissions("module:cud")
    public Result move(@ModelAttribute("t") Module t, String overId, String position) throws Exception {
        return super.move(t, overId, position);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @GetMapping("delete/{id}")
    @RequiresPermissions("module:cud")
    public Result delete(@ModelAttribute("t") Module t) throws Exception {
        return super.delete(t);
    }
    
    /**
     * 完整树
     * @return
     */
    @GetMapping("tree")
    @RequiresPermissions("module:r|code:r")
    public List<Module> tree() {
        return service.tree();
    }
}
