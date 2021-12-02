package com.gh.ghdg.api.config;

import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import com.gh.ghdg.common.security.AccountInfo;
import com.gh.ghdg.common.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 审计用户
 */
@Configuration
public class UserAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserService userService;
    
    /**
     * 从用户服务中，根据id查询出来用户实体，再保存。
     * @return 返回用户Optional
     */
    @Override
    public Optional<User> getCurrentAuditor() {
        User user;
        try { // 调试时没有 SecurityManager
            AccountInfo accountInfo = JwtUtil.getAccountInfo();
            String id = accountInfo.getId();
            user = userService.one(id).get();
        } catch(Exception e) {
            user = null;
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

}
