package com.gh.ghdg.sysMgr.bean.enums;

import com.gh.ghdg.sysMgr.bean.IBaseEnum;
import com.gh.ghdg.sysMgr.bean.entities.EnumConverter;

public class TypeEnum {
    
    public enum MenuType implements IBaseEnum<Integer> {
        menu(1),button(0),module(3);

        private int value;
    
        MenuType(int value) {
            this.value = value;
        }
    
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
