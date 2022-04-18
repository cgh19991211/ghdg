package com.gh.ghdg.sysMgr.bean.constant;

public interface PermissionCode {
    //系统管理
    String SYS = "sys";
    String USER = "user";//查
    String USER_EDIT = "user.edit";//增删改
    String USER_ADD = "user.add";
    String USER_DELETE = "user.delete";
    String MENU = "menu";
    String MENU_EDIT = "menu.edit";
    String MENU_ADD = "menu.add";
    String MENU_DELETE = "menu.delete";
    String ROLE = "role";
    String ROLE_EDIT = "role.edit";
    String ROLE_ADD = "role.add";
    String ROLE_DELETE = "role.delete";
    String PERMISSION = "permission";
    String PERMISSION_EDIT = "permission.edit";//权限的保存需要保存菜单id，所以不需要设置权限分配给菜单的权限
    String PERMISSION_ADD = "permission.add";
    String PERMISSION_DELETE = "permission.delete";
    String ROLE_MENU = "role.menu";
    String ROLE_MENU_ADD = "role.menu.add";//角色分配给菜单
    String USER_ROLE = "user.role";
    String USER_ROLE_ADD = "user.role.add";//角色分配给用户
    String NOTICE = "notice";
    String NOTICE_EDIT = "notice.edit";
    String NOTICE_ADD = "notice.add";
    String NOTICE_DELETE = "notice.delete";
    
    //cms
    String CMS = "cms";
    //opr
    String OPR = "opr";
    //business
    String BUSINESS = "business";
    String BLOG_DELETE = "blog.delete";
    String BLOG_FREEZE = "blog.freeze";
    
}
