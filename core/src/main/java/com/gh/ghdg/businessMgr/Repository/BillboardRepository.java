package com.gh.ghdg.businessMgr.Repository;

import com.gh.ghdg.businessMgr.bean.entities.Billboard;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillboardRepository extends BaseMongoRepository<Billboard> {
}
