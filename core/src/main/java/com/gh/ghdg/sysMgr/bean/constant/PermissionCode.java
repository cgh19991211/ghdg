package com.gh.ghdg.sysMgr.bean.constant;

public interface PermissionCode {
    //系统管理
    String SYS = "sys";
    String USER = "user";//查
    String USER_EDIT = "user.edit";//增删改
    String MENU = "menu";
    String MENU_EDIT = "menu.edit";
    String ROLE = "role";
    String ROLE_EDIT = "role.edit";
    String PERMISSION = "permission";
    String PERMISSION_EDIT = "permission.edit";//权限的保存需要保存菜单id，所以不需要设置权限分配给菜单的权限
    String ROLE_MENU = "role.menu";
    String ROLE_MENU_EDIT = "role.menu.edit";//角色分配给菜单
    String USER_ROLE = "user.role";
    String USER_ROLE_EDIT = "user.role.add";//角色分配给用户
    String NOTICE = "notice";
    String NOTICE_EDIT = "notice.edit";
    
    //cms
    String CMS = "cms";
    //opr
    String OPR = "opr";
    //business
    String BUSINESS = "business";
    
}
