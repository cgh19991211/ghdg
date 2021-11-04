package com.gh.ghdg.api.utils;

import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.common.utils.Result;
import lombok.Data;

@Data
public class LoginResult extends Result {

    private String field;
    private boolean first;
    private User user;

    public static LoginResult suc(String message, User user) {
        LoginResult r = new LoginResult();
        r.setSuccess(true);
        r.setMessage(message);
        r.setUser(user);
        return r;
    }

    public static LoginResult err(String message, boolean first, String field) {
        LoginResult r = new LoginResult();
        r.setSuccess(false);
        r.setMessage(message);
        r.setFirst(first);
        r.setField(field);
        return r;
    }
}
