package com.gh.fileserver.domain.VO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileVo {
    String msg = "";
    Integer code = 0;
    Data data = new Data();
    
    public class Data{
        Set<String> errFiles = new HashSet<>();
        Map<String,String> succMap = new HashMap<>();
    
        public Data() {
        }
    
        public Data(Set<String> errFiles, Map<String, String> succMap) {
            this.errFiles = errFiles;
            this.succMap = succMap;
        }
    
        public Set<String> getErrFiles() {
            return errFiles;
        }
    
        public void setErrFiles(Set<String> errFiles) {
            this.errFiles = errFiles;
        }
    
        public Map<String, String> getSuccMap() {
            return succMap;
        }
    
        public void setSuccMap(Map<String, String> succMap) {
            this.succMap = succMap;
        }
    
        @Override
        public String toString() {
            return "Data{" +
                    "errFiles=" + errFiles +
                    ", succMap=" + succMap +
                    '}';
        }
    }
    
    public FileVo() {
    }
    
    public FileVo(String msg, Integer code, Data data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public Data getData() {
        return data;
    }
    
    public void setData(Data data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "FileVo{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
