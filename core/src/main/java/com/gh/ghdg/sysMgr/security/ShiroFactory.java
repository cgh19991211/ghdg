package com.gh.ghdg.sysMgr.security;

import com.gh.ghdg.common.utils.SpringContextUtil;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.bean.enums.Status;
import com.gh.ghdg.sysMgr.core.dao.system.MenuDao;
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
import java.util.*;

@Service
@DependsOn("springContextUtil")
@Transactional(readOnly = true)
public class ShiroFactory {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;
    
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
        shiroUser.setId(user.getId());//用户id
        shiroUser.setAccount(user.getUsername());//用户名
//        shiroUser.setPassword(user.getPassword());
        shiroUser.setNickname(user.getNickname());//昵称
        shiroUser.setAvatar(user.getAvatar());
        shiroUser.setEmail(user.getEmail());
        shiroUser.setPhone(user.getPhone());
        
        //roles
        List<ShiroRole> roleList = shiroRoles(user);//角色列表，角色对象包括id，名字，code
        shiroUser.setRoles(roleList);
        //permissions && menus
        shiroMenus(user,shiroUser);
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
    
    /**
     * id,icon,pid(0||pid),name,url,level,,type,seq,code,status
     * @param user
     * @return
     */
    public void shiroMenus(User user,ShiroUser shiroUser){
        List<UserRole> userRoles = user.getUserRoles();
        Set<ShiroMenu> menuSet = new HashSet<>();
        for(UserRole ur:userRoles){
            //角色实体类中虽然有permissions字段，但没办法直接查出来，
            // 应该是跟表结构有关系，role表跟permission表实际上并没有关联，
            // 而是通过RoleMenuPermission这个中间表来的关联
            Role role = ur.getRole();
            //所以，要获取权限，还是得根据菜单来，毕竟在权限表里就有菜单的外键
            List<RoleMenu> roleMenus = role.getRoleMenus();
            Set<ShiroPermission> permSet = new HashSet<>();
            for(RoleMenu rm:roleMenus){
                Menu m = rm.getMenu();//id,pid,name,code,tip,seq,type,isopen,icon,status
                //TODO: menu -> shiroMenu
                ShiroMenu sm = new ShiroMenu();
                sm.setId(m.getId());
                Menu parent = m.getParent();
                if(parent!=null)
                    sm.setPcode(parent.getMenuCode());
                sm.setPcodes(recurPcode(m));
                sm.setMenuName(m.getMenuName());
                sm.setTips(m.getTip());
                sm.setDisplay_seq(m.getDisplaySeq());
                sm.setIsmenu(m.getType().getValue());
                sm.setIcon(m.getIcon());
                sm.setStatus(m.getStatus().getValue());
                List<Permission> permissions = m.getPermissions();
                for(Permission p:permissions){
                    sm.setUrl(p.getUrl());
                }
                menuSet.add(sm);
                
                shiroPermissions(m,permSet);
            }
            shiroUser.setPermissions(permSet);
        }
        shiroUser.setMenus(menuSet);
    }
    
    private String[] recurPcode(Menu m){
        Menu menu = m;
        ArrayList<String> res = new ArrayList<>();
        if(menu.getParent()!=null){
            res.add(menu.getParent().getMenuCode());
            menu = menu.getParent();
        }
        return Arrays.stream(res.toArray()).toArray(String[]::new);
    }
    
    public void shiroPermissions(Menu menu,Set<ShiroPermission> permSet){
        List<Permission> permissions = menu.getPermissions();
        for(Permission p:permissions){
            ShiroPermission sp = new ShiroPermission();
            sp.setPermissionCode(p.getPermissionCode());
            sp.setUrl(p.getUrl());
            permSet.add(sp);
        }
    }
    
}
