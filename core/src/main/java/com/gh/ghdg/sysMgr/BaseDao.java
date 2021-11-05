package com.gh.ghdg.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // 不实例化
public interface BaseDao<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
