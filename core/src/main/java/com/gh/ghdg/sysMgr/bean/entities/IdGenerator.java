package com.gh.ghdg.sysMgr.bean.entities;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.sysMgr.BaseEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDHexGenerator;

import java.io.Serializable;

public class IdGenerator extends UUIDHexGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        String id = ((BaseEntity) obj).getId();
        if(StrUtil.isNotEmpty(id)) {
            return id;
        }
        return super.generate(s, obj);
    }
}
