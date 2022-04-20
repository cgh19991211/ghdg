package com.gh.ghdg.common.utils;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PwdEncrypt {
    
    @Autowired
    private HashedCredentialsMatcher matcher;
    /**
     * 加密密码
     * @param password
     * @param salt
     * @return hashed password
     */
    public String encryptPassword(String password, String salt) {
        return new SimpleHash(matcher.getHashAlgorithmName(), password, salt, matcher.getHashIterations()).toString();
    }
}
