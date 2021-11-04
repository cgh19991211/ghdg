package com.gh.ghdg.api.controller;

import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.core.dao.TreeDao;
import com.gh.ghdg.sysMgr.core.service.TreeService;


public class TreeController<T extends TreeEntity<T>, S extends TreeDao<T>, R extends TreeService<T, S>> extends DisplaySeqController<T, S, R> {


}
