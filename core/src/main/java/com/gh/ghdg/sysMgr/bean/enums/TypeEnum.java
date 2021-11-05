package com.gh.ghdg.sysMgr.bean.enums;

import com.gh.ghdg.sysMgr.bean.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.entities.EnumConverter;
import lombok.AllArgsConstructor;

public class TypeEnum {
    
    @AllArgsConstructor
    public enum MenuType implements IBaseEnum<Integer> {
        menu(0),button(1),module(3);

        private int value;
    
        @Override
        public Integer getValue() {
            return this.value;
        }
        public void setValue(int value){
            this.value = value;
        }
    
        public static class Converter extends EnumConverter<MenuType, Integer> {
            public Converter() {
                super(MenuType.class);
            }
        }
    }
    
}
