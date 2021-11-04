package com.gh.ghdg.sysMgr.bean.enums;

import com.gh.ghdg.sysMgr.bean.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.entities.EnumConverter;
import lombok.Getter;

public enum BooleanStatus implements IBaseEnum<Boolean> {
    生效(true),
    失效(false);

    @Getter
    private final Boolean value;

    BooleanStatus(Boolean value) {
        this.value = value;
    }

    public static class Converter extends EnumConverter<BooleanStatus, Boolean> {
        public Converter() {
            super(BooleanStatus.class);
        }
    }
}
