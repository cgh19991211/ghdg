package com.gh.ghdg.sysMgr.bean.enums;

import com.gh.ghdg.common.enums.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.entities.EnumConverter;

public enum BooleanStatus implements IBaseEnum<Boolean> {
    生效(true),
    失效(false);

    private final Boolean value;
    
    @Override
    public Boolean getValue() {
        return value;
    }
    
    BooleanStatus(Boolean value) {
        this.value = value;
    }

    public static class Converter extends EnumConverter<BooleanStatus, Boolean> {
        public Converter() {
            super(BooleanStatus.class);
        }
    }
}
