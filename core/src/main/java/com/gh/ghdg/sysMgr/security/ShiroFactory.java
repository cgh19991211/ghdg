package com.gh.ghdg.sysMgr.security;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class ShiroFactory {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    
    public static ShiroFactory me(){return SpringContextUtil.getBean(ShiroFactory.class);}
    
    public User user(String account) throws CredentialException {
        User user = userDao.findByUsername(account);
        if(null==user){
            throw new CredentialsException();
        }
        if(user.getStatus()!= Status.生效){
            throw new LockedAccountException();
        }
        return user;
    }
    
    /**
     * 根据实体user创建shiroUser，包含角色，权限等信息。（没放密码）
     * @param user
     * @return
     */
    public ShiroUser shiroUser(User user){
        //1. 先从缓存取
        
        //2. 再把user包装成ShiroUser
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());
        shiroUser.setAccount(user.getUsername());
//        shiroUser.setPassword(user.getPassword());
        shiroUser.setNickname(user.getNickname());
        //role
        List<ShiroRole> roleList = shiroRoles(user);
        shiroUser.setRoleList(roleList);
        //permission
        Set<ShiroPermission> shiroPermissions = shiroPermissions(user);
        shiroUser.setPermissions(shiroPermissions);
        return shiroUser;
    }
    
    public List<ShiroRole> shiroRoles(User user){
        List<ShiroRole> list = new ArrayList<>();
        List<UserRole> userRoles = user.getUserRoles();
        for(UserRole ur: userRoles){
            Role role = ur.getRole();
            ShiroRole sr = new ShiroRole();
            sr.setId(role.getId());
            sr.setRoleName(role.getRoleName());
            sr.setRoleCode(role.getRoleCode());
            list.add(sr);
        }
        return list;
    }
    
    public Set<ShiroPermission> shiroPermissions(User user){
        Set<ShiroPermission> list = new HashSet<>();
        List<UserRole> userRoles = user.getUserRoles();
        for(UserRole ur:userRoles){
            //角色实体类中虽然有permissions字段，但没办法直接查出来，
            // 应该是跟表结构有关系，role表跟permission表实际上并没有关联，
            // 而是通过RoleMenuPermission这个中间表来的关联
            Role role = ur.getRole();
            //所以，要获取权限，还是得根据菜单来，毕竟在权限表里就有菜单的外键
            List<RoleMenu> roleMenus = role.getRoleMenus();
            for(RoleMenu rm:roleMenus){
                Menu menu = rm.getMenu();
                List<Permission> permissions = menu.getPermissions();
                for(Permission p:permissions){
                    ShiroPermission shiroPermission = new ShiroPermission();
                    shiroPermission.setPermissionCode(p.getPermissionCode());
                    shiroPermission.setUrl(p.getUrl());
                    list.add(shiroPermission);
                }
            }
        }
        return list;
    }
}
