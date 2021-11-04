package com.gh.ghdg.api.controller;

import com.gh.ghdg.sysMgr.bean.DisplaySeqEntity;
import com.gh.ghdg.sysMgr.core.dao.DisplaySeqDao;
import com.gh.ghdg.sysMgr.core.service.DisplaySeqService;
import com.gh.ghdg.common.utils.Result;
import org.springframework.web.bind.annotation.ModelAttribute;

public class DisplaySeqController<T extends DisplaySeqEntity, S extends DisplaySeqDao<T>, R extends DisplaySeqService<T, S>> extends BaseController<T, S, R> {

    /**
     * 移动
     * @param t
     * @param overId
     * @return
     * @throws Exception
     */
    public Result move(@ModelAttribute("t") T t, String overId, String position) throws Exception {
        return Result.moveSuc(service.move(t, overId, position));
    }

}
