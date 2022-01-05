package com.gh.ghdg.businessMgr.bean.entities;

import com.google.auto.value.AutoValue;
import org.springframework.data.mongodb.core.mapping.Document;

@AutoValue
@Document
abstract public class TestAutoValue extends BaseMongoEntity{
    abstract public String name();
    abstract public String pwd();
}
