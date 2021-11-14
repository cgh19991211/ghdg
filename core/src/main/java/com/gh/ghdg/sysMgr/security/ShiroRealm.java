package com.gh.ghdg.sysMgr.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gh.ghdg.common.MongoRepository;
import com.gh.ghdg.common.utils.HttpKit;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.service.system.MenuService;
import com.gh.ghdg.sysMgr.core.service.system.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoRepository mongoRepository;
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private UserDao userDao;
    
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;//具体类型取决于所使用的token的类型。
    }

    /**
     * 授权
     * 为当前登录成功的用户授予权限和分配角色
     *
     * @param principals 授权信息的主体
     * @return 返回与授权信息想联系的主体
     * @see SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        User user = null;
        
        String username = JwtUtil.getUsername(principals.toString());
        user = userService.findByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //TODO: get roles
        List<UserRole> userRoles = user.getUserRoles();
        List<Role> roleList = new ArrayList<>();
        List<String> roleCodeList = new ArrayList<>();
        for(UserRole ur:userRoles){
            Role tmp = ur.getRole();
            roleList.add(tmp);
            roleCodeList.add(tmp.getRoleCode());
        }
        simpleAuthorizationInfo.addRoles(roleCodeList);//角色编码
        
        //TODO: get permissions
        List<RoleMenu> roleMenus = new ArrayList<>();
        List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
        Set<Permission> permissionList = new HashSet<>();
        for(Role r: roleList){
            for(RoleMenu rm:r.getRoleMenus()){
                roleMenuPermissions = rm.getRoleMenuPermissions();
                for(RoleMenuPermission rmp:roleMenuPermissions){
                    permissionList.add(rmp.getPermission());
                }
            }
            
        }
        Set<String> permissionCodes = new HashSet<>();
        for(Permission p:permissionList){
            permissionCodes.add(p.getPermissionCode());
        }
        simpleAuthorizationInfo.addStringPermissions(permissionCodes);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * 验证当前登录的用户，获取认证信息
     *
     * @param  auth--自定义的JwtToken
     * @return an {@link AuthenticationInfo} 查询成功之后，从AuthenticationInfo中返回用户账户信息
     * @throws AuthenticationException 查询或者授权逻辑发生错误则抛出这个异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException, TokenExpiredException {
        
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        AccountInfo accountInfo = JwtUtil.getAccountInfo(token);
        if (accountInfo == null) {
            throw new AuthenticationException("token invalid");
        }
        User user = null;
            user = userDao.findByUsername(accountInfo.getUsername());//这里应该用缓存？？？
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }
    
//        if (!JwtUtil.verify(token, accountInfo.getUsername(), user.getPassword())) {
//            throw new AuthenticationException("Username or password error");
//        }
        
        //参考login controller做try catch判定token是否过期或有效
        JwtUtil.verify(token, accountInfo.getUsername(), user.getPassword());
    
        //SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)，
        // 这里principal用于标识用户，比如用户账号，credentials用于验证，比如密码（这里可以是类似md5加密后的）。
        //比如用户jim，密码是123。
        //登录时会以UsernamePasswordToken(jim,123)的传入，
        //realm中有个CredentialsMatcher(可配置，可以对password进行加密)。
        //根据这个Token和SimpleAuthenticationInfo的credentials做对比，就能进行验证。
        /**
         * 自定义Realm类的父类AuthenticatingRealm会执行assertCredentialsMatch方法进行Credentials的校验。
         */
        return new SimpleAuthenticationInfo(token, token, "my_realm"); // 此处 credentials 是盐值加密后的
    }
}
