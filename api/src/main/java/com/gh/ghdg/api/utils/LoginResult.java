package com.gh.ghdg.api.utils;

import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.common.utils.Result;
import lombok.Data;

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
    
    public LoginResult() {
    }
    
    public LoginResult(String field, boolean first, User user) {
        this.field = field;
        this.first = first;
        this.user = user;
    }
    
    public String getField() {
        return field;
    }
    
    public void setField(String field) {
        this.field = field;
    }
    
    public boolean isFirst() {
        return first;
    }
    
    public void setFirst(boolean first) {
        this.first = first;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "LoginResult{" +
                "field='" + field + '\'' +
                ", first=" + first +
                ", user=" + user +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
