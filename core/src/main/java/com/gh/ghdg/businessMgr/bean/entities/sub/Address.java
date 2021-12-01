package com.gh.ghdg.businessMgr.bean.entities.sub;

public class Address {
    private String provence;
    private String city;
    private String county;
    private String detail;
    
    public Address() {
    }
    
    public Address(String provence, String city, String county, String detail) {
        this.provence = provence;
        this.city = city;
        this.county = county;
        this.detail = detail;
    }
    
    public String getProvence() {
        return provence;
    }
    
    public void setProvence(String provence) {
        this.provence = provence;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCounty() {
        return county;
    }
    
    public void setCounty(String county) {
        this.county = county;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "provence='" + provence + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
