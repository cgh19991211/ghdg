package com.gh.ghdg.sysMgr.core.dao;

import com.gh.ghdg.sysMgr.bean.TreeEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TreeDao<T extends TreeEntity> extends DisplaySeqDao<T> {

}
