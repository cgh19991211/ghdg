package com.gh.ghdg.sysMgr.bean.enums;

public class EnumException extends RuntimeException {

    public EnumException(Class<?> clazz, Object value) {
        super("枚举 " + clazz.getSimpleName() + " 没有成员 " + value);
    }

}
