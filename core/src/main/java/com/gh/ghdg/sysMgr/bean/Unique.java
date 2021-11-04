package com.gh.ghdg.sysMgr.bean;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Unique {

    String name();

    String[] extraFields() default { };

}
