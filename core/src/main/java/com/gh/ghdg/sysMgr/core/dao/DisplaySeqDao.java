package com.gh.ghdg.sysMgr.core.dao;

import com.gh.ghdg.common.BaseDao;
import com.gh.ghdg.sysMgr.bean.DisplaySeqEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // 不实例化
public interface DisplaySeqDao<T extends DisplaySeqEntity> extends BaseDao<T> {
}
