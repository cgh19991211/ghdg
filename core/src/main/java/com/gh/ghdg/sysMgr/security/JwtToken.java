package com.gh.ghdg.sysMgr.security;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 获取账号
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 获取密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
