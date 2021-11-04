package com.gh.ghdg.sysMgr.security;

import lombok.*;

@Setter
@Getter
public class AccountInfo {
    private String id;
    private String username;
    
    public AccountInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
