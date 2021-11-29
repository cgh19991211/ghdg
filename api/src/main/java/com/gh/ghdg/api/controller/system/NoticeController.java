package com.gh.ghdg.api.controller.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.api.controller.BaseController;
import com.gh.ghdg.common.utils.Result;
import com.gh.ghdg.sysMgr.bean.constant.PermissionCode;
import com.gh.ghdg.sysMgr.bean.entities.system.Notice;
import com.gh.ghdg.sysMgr.core.dao.system.NoticeDao;
import com.gh.ghdg.sysMgr.core.service.system.NoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/notice")
public class NoticeController extends BaseController<Notice, NoticeDao,NoticeService> {
    @Autowired
    private NoticeService noticeService;
    
    @RequiresPermissions(PermissionCode.NOTICE)
    @RequestMapping(value = "/list")
    public Result noticeList(String condition){
        List<Notice> list = null;
        if(StrUtil.isEmptyIfStr(condition)){
            list = service.queryAll();
        }else{
            list = noticeService.findByTitleLike(condition);
        }
        return Result.suc(list);
    }
    
    @PostMapping(value = "/save")
    @RequiresPermissions(value = {PermissionCode.NOTICE_EDIT,PermissionCode.NOTICE_ADD})
    public Result noticeSave(@ModelAttribute("t") Notice t)throws Exception{
        return super.save(t);
    }
}
