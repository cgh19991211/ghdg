package com.gh.ghdg.businessMgr.bean.dto;

public class ChangePasswordDto {
    String code;
    String pwd;
    
    public ChangePasswordDto() {
    }
    
    public ChangePasswordDto(String code, String pwd) {
        this.code = code;
        this.pwd = pwd;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    @Override
    public String toString() {
        return "ChangePasswordDto{" +
                "code='" + code + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
