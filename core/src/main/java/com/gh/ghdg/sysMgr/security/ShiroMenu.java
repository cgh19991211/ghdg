package com.gh.ghdg.sysMgr.security;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 封装成waimai项目里的menu
 * 数据在数据库中创建视图查询
 */
public class ShiroMenu implements Serializable {
    private static long serialVersionUID = 1L;
    //查询菜单：id,icon,pid(0||pid),name,url,level,,type,seq,code,status
    private String id;
    private String menuName;
    private String menuCode;
    private String icon;
    private Integer ismenu;//type: 1-menu  o-button
    private Integer isopen;//是否默认打开
    private String level;//级别
    private Integer display_seq;
    private Integer status;
    private String tips;
    private String url;//资源路径，从权限中获取
    private String pcode;
    private String[] pcodes;
    
    public ShiroMenu() {
    }
    
    public ShiroMenu(String id, String menuName, String menuCode, String icon, Integer ismenu, Integer isopen, String level, Integer display_seq, Integer status, String tips, String url, String pcode, String[] pcodes) {
        this.id = id;
        this.menuName = menuName;
        this.menuCode = menuCode;
        this.icon = icon;
        this.ismenu = ismenu;
        this.isopen = isopen;
        this.level = level;
        this.display_seq = display_seq;
        this.status = status;
        this.tips = tips;
        this.url = url;
        this.pcode = pcode;
        this.pcodes = pcodes;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public static void setSerialVersionUID(long serialVersionUID) {
        ShiroMenu.serialVersionUID = serialVersionUID;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public String getMenuCode() {
        return menuCode;
    }
    
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public Integer getIsmenu() {
        return ismenu;
    }
    
    public void setIsmenu(Integer ismenu) {
        this.ismenu = ismenu;
    }
    
    public Integer getIsopen() {
        return isopen;
    }
    
    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public Integer getDisplay_seq() {
        return display_seq;
    }
    
    public void setDisplay_seq(Integer display_seq) {
        this.display_seq = display_seq;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getTips() {
        return tips;
    }
    
    public void setTips(String tips) {
        this.tips = tips;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getPcode() {
        return pcode;
    }
    
    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    
    public String[] getPcodes() {
        return pcodes;
    }
    
    public void setPcodes(String[] pcodes) {
        this.pcodes = pcodes;
    }
    
    @Override
    public String toString() {
        return "ShiroMenu{" +
                "id='" + id + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", icon='" + icon + '\'' +
                ", ismenu=" + ismenu +
                ", isopen='" + isopen + '\'' +
                ", level='" + level + '\'' +
                ", display_seq=" + display_seq +
                ", status=" + status +
                ", tips='" + tips + '\'' +
                ", url='" + url + '\'' +
                ", pcode='" + pcode + '\'' +
                ", pcodes=" + Arrays.toString(pcodes) +
                '}';
    }
    
}
