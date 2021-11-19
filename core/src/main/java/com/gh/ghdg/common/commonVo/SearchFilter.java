package com.gh.ghdg.common.commonVo;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义查询条件：fieldName LT,LTE,GT,GTE,EQ,LIKE,IN,ISNULL,NOTNULL value
 * 通过DynamicSpecifications转换为JPA的Predicate实例，再构建Specification
 */
public class SearchFilter {
    public enum Operator{
        LT,LTE,GT,GTE,EQ,LIKE,IN,ISNULL,NOTNULL
    }
    public String fieldName;
    public Object value;
    public Operator operator;
    
    public SearchFilter() {
    }
    
    public SearchFilter(String fieldName, Operator opr){
        this.fieldName = fieldName;
        this.operator = opr;
    }
    public SearchFilter(String fieldName, Operator opr, Object obj){
        this.fieldName = fieldName;
        this.operator = opr;
        this.value = obj;
    }
    
    public static SearchFilter build(String fieldName, Operator opr){
        return new SearchFilter(fieldName,opr);
    }
    public static SearchFilter build(String fieldName,Operator opr, Object obj){
        return new SearchFilter(fieldName,opr,obj);
    }
    
    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     * @param searchParams
     * @return
     */
    public static Map<String,SearchFilter> parse(Map<String, Object> searchParams){
        Map<String,SearchFilter> filters = new HashMap<>();
        for (Map.Entry<String,Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
    
            // 拆分operator与filedAttribute
            String[] names = StrUtil.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);
    
            // 创建searchFilter
            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }
        return filters;
    }
}
