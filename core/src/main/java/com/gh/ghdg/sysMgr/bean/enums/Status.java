package com.gh.ghdg.sysMgr.bean.enums;

import com.gh.ghdg.sysMgr.bean.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.entities.EnumConverter;
import lombok.Getter;

public enum Status implements IBaseEnum<Integer> {
    
    生效(0),
    失效(1);

    @Getter
    private Integer value;

    Status(Integer value) {this.value = value;
    }

    public Status ok() {
        if(this == 生效) {
            throw new RuntimeException("已经是生效状态");
        }
        return 生效;
    }

    public Status ban() {
        if(this != 生效) {
            throw new RuntimeException("非生效状态");
        }
        return 失效;
    }
    
    public boolean isOk(){return this==生效;}

    public static class Converter extends EnumConverter<Status, Integer> {
        public Converter() {
            super(Status.class);
        }
    }
}
