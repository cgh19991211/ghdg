package com.gh.ghdg.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    
    //json转换
    static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T parseJackson(String json,Class<T> clazz){
        try {
            T t = objectMapper.readValue(json, clazz);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
