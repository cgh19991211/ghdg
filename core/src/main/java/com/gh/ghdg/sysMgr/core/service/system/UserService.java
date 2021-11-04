package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.common.BaseService;
import com.gh.ghdg.common.utils.ToolUtil;
import com.gh.ghdg.common.utils.constant.CommonRex;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserRoleDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService extends BaseService<User, UserDao> {

    @Autowired
    private HashedCredentialsMatcher matcher;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    /*@Autowired
    private MobTokenDao mobTokenDao;*/

//    @Autowired
//    private InfoService infoService;
    @Autowired
    private UserRoleService userRoleService;
    /**
     * 保存
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public User save(User t) throws Exception{
        //TODO:检查用户名,手机号,email是否唯一
        String id = t.getId();
        check(id,t.getUsername(),"用户名");
        //check(id, t.getIdNo(), "身份证号");
        //check(id, t.getCellphoneNo(), "手机号");
        //check(id, t.getEmail(), "Email");
        if(StringHelper.isEmpty(t.getId())){
            String salt = ToolUtil.getRandomString(4);
            t.setSalt(salt);
            this.encryptPassword(t);
        }
        return super.save(t);
    }

    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @Transactional
    public List<User> delete(User t) throws Exception {
        return super.delete(t);
    }

    //TODO: 分页查询用户，按角色查询用户
    
    //这个方法是用来给LoginController做登陆用的，应该走数据库查询，不能用缓存。
//    @Cacheable(value = CacheName.APPLICATION,key = "#root.targetClass.simpleName+':'+#id",unless = "#result==null")
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    /**
     * 保存分配角色
     * @param t
     * @param roleIds
     * @return
     */
    @Transactional
    public void saveRoles(User t, String roleIds) {
        if (StringHelper.isNotEmpty(roleIds)) {
            for (String roleId : StringHelper.split(",",roleIds)) {
                Optional<Role> opt = roleDao.findById(roleId);
                if (!opt.isPresent()) {
                    throw new MyException("ID " + roleId + " 角色不存在");
                }
                Role r = opt.get();
                userRoleService.save(t, r);
            }
        }
    }
//    /**
//     * 保存分配角色
//     * @param t
//     * @param roleIds
//     * @return
//     */
//    @Transactional
//    public void saveRoles(User t, String roleNames) {
//        if (StringHelper.isNotEmpty(roleIds)) {
//            for (String roleName : StringHelper.split(",",roleNames)) {
//                Optional<Role> opt = roleDao.findByRoleName(roleName);
//                if (!opt.isPresent()) {
//                    throw new MyException("ID " + roleId + " 角色不存在");
//                }
//                Role r = opt.get();
//                userRoleService.save(t, r);
//            }
//        }
//    }

    /**
     * 删除分配角色
     * @param t
     * @param roleIds
     */
    @Transactional
    public void deleteRoles(User t, String roleIds) {
        if (StringHelper.isNotEmpty(roleIds)) {
            for (String roleId : StringHelper.split(",",roleIds)) {
                Role m = new Role();
                m.setId(roleId);
                UserRole ur = userRoleDao.findByUserAndRole(t, m);
                if (null != ur) {
                    userRoleDao.delete(ur);
                }
            }
        }
    }

//    @Transactional
//    public User login(User user){
//        String username = user.getUsername();
//        User dbUser = userDao.findByUsername(username);
//        String password = encryptPassword(user.getPassword(),dbUser.getSalt());
//
//
//
//        // 缓存
//        user = dao.findByUsername(username);
//        user.setLastLoginDate(new Date());
//        user.setIp(ContextHelper.getRemoteIp());
//        ContextHelper.setCurUser(user);
//        return user;
//    }

    /**
     * 检查用户名、手机号、Email或身份证号是否被使用
     * @param id
     * @param value
     */
    protected void check(String id, String value, String name){

    }

    /**
     * 加密密码
     * @param t
     */
    @Transactional
    protected void encryptPassword(User t) {
        String password = t.getPassword();
        // 验证
        checkValid(password);
        // 加密
        String encrypt = encryptPassword(password, t.getSalt());
        t.setPassword(encrypt);
    }

    /**
     * 加密密码
     * @param password
     * @param salt
     * @return
     */
    public String encryptPassword(String password, String salt) {
        return new SimpleHash(matcher.getHashAlgorithmName(), password, salt, matcher.getHashIterations()).toString();
    }
    
    /**
     * 重置密码
     * @param t
     */
    @Transactional
    public void resetPassword(User t) {
        this.encryptPassword(t);
    }

    /**
     * 检查密码是否有效
     * @param password
     * @return
     */
    public boolean checkValid(String password){
        return isValid(password,true);
    }

    /**
     * 密码是否有效
     *
     * @param password
     * @return
     * @throws Exception
     */
    public boolean isValid(String password) {
        return isValid(password, false);
    }

    /**
     * 密码是否有效
     * @param password
     * @param check
     * @return
     * @throws Exception
     */
    public boolean isValid(String password, boolean check) {
        Pattern pattern = Pattern.compile(CommonRex.password);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            if (check) {
                throw new MyException(CommonRex.passwordText);
            }
            return false;
        }
        return true;
    }
}
