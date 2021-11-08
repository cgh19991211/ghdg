package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.utils.ReflectHelper;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.common.utils.ToolUtil;
import com.gh.ghdg.common.utils.constant.CommonRex;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.Role;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import com.gh.ghdg.sysMgr.bean.entities.system.UserRole;
import com.gh.ghdg.sysMgr.core.dao.system.RoleDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserDao;
import com.gh.ghdg.sysMgr.core.dao.system.UserRoleDao;
import com.gh.ghdg.sysMgr.security.JwtUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
        check(t);
        String id = t.getId();
        /**
         * id为空，则是新增用户，否则是修改用户信息。
         */
        if(StrUtil.isEmpty(id)){
            String password = t.getPassword();
            checkValid(password);
            String salt = ToolUtil.getRandomString(4);
            t.setSalt(salt);
            String encryptPassword = this.encryptPassword(password, salt);
            t.setPassword(encryptPassword);
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

    /**
     * 检查用户名、手机号、Email是否被使用
     * @param id
     * @param value
     */
    protected void check(User user){
        String id = user.getId();
        String username = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();
        Specification s1 = (Specification<User>)(root,query,cb)->{
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("username"),username));
            list.add(cb.equal(root.get("phone"),phone));
            list.add(cb.equal(root.get("email"),email));
            Predicate[] ps = new Predicate[list.size()];
            return cb.or(list.toArray(ps));
        };
        //如果前端传进来的用户，带上了id，那就先检测id是否为空，
        // 不为空的话需要查询id不等与传进来的id的用户，
        Specification s2 = null;
        if (StringHelper.isNotEmpty(id)) {
            s2 = (Specification<User>) (root, query, cb) -> {
                Predicate p = cb.notEqual(root.get("id"), id);
                return cb.and(p);
            };
        }
    
        List<User> all = dao.findAll(s1.and(s2));
        StringBuffer usedFields = new StringBuffer();usedFields.append('[');
        StringBuffer usedValues = new StringBuffer();usedValues.append(('['));
        for(User u:all){
            String username1 = u.getUsername();
            String phone1 = u.getPhone();
            String email1 = u.getEmail();
            if(StrUtil.equals(username,username1)){
                usedFields.append("username").append(',');
                usedValues.append(username).append(',');
            }else if(StrUtil.equals(phone1,phone)) {
                usedFields.append("phone").append(',');
                usedValues.append(phone).append(',');
            } else if(StrUtil.equals(email1,email)) {
                usedFields.append("email").append(',');
                usedValues.append(email).append(',');
            }
        }
        if (all.size() > 0) {
            usedFields.replace(usedFields.length()-1,usedFields.length(),"]");
            usedValues.replace(usedValues.length()-1,usedValues.length(),"]");
            throw new MyException(usedFields.toString() + " " + usedValues.toString() + " 已被使用");
        }
    }

    /**
     * 加密密码 根据当前用户盐
     * @param
     * @Return Hashed pwd
     */
    protected String encryptPassword(String password) {
        String encrypt = encryptPassword(password, JwtUtil.getCurUser().getSalt());
        // 验证
        checkValid(password);
        // 加密
        return encrypt;
    }

    /**
     * 加密密码
     * @param password
     * @param salt
     * @return hashed password
     */
    public String encryptPassword(String password, String salt) {
        return new SimpleHash(matcher.getHashAlgorithmName(), password, salt, matcher.getHashIterations()).toString();
    }
    
    /**
     * 重置密码
     * @param
     */
    @Transactional
    public void resetPassword(String password) {
        checkValid(password);
        User curUser = JwtUtil.getCurUser();
        String salt = curUser.getSalt();
        String encryptedPassword = this.encryptPassword(password,salt);
        String oldPassword = curUser.getPassword();
        if(StrUtil.equals(oldPassword,encryptedPassword)){
            throw new MyException("新旧密码不能一样");
        }
        curUser.setPassword(encryptedPassword);
        dao.save(curUser);
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
