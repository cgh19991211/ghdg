package com.gh.ghdg.sysMgr.bean.entities;

import com.gh.ghdg.common.enums.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.enums.EnumException;

import javax.persistence.AttributeConverter;

public abstract class EnumConverter<T extends Enum<T> & IBaseEnum<S>, S> implements AttributeConverter<T,S> {
    private final Class<T> enumType;

    protected EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public S convertToDatabaseColumn(T attribute) {
        return null !=  attribute ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(S source) {
        if(source==null||"".equals(source)){
            return null;
        }
        for(T e : enumType.getEnumConstants()) {
            if(source.equals(e.getValue())) {
                return e;
            }
        }
        throw new EnumException(enumType, source);
    }
}
