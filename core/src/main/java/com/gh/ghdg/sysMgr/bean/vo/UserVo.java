package com.gh.ghdg.sysMgr.bean.vo;

import com.gh.ghdg.sysMgr.bean.entities.system.User;

import java.util.Date;

public class UserVo extends User {
    private String username;
    private String nickname;
    private String gender;
    private String rolename;
    private String email;
    private String phone;
    private Date createdDate;
    private String status;
}
