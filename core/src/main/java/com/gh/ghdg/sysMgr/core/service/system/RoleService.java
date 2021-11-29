package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.core.dao.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService extends BaseService<Role, RoleDao> {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MenuDao menuDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private RoleMenuService roleMenuService;
    
    @Autowired
    private RoleMenuPermissionService roleMenuPermissionService;
    
    @Autowired
    private PermissionDao permissionDao;

    @Transactional
    public List<Role> findByRoleName(String name){
        return dao.findByRoleName(name);
    }
    
    /**
     * 完整树
     * @param t
     * @return
     */
    public List<Role> tree() {
        return getRoots();
    }
    
    /**
     * 返回所有根角色--父角色为null
     * @param t
     * @return
     */
    protected List<Role> getRoots() {
        SearchFilter filter = SearchFilter.build("parent", SearchFilter.Operator.ISNULL);
        return queryAll(filter);
    }
    
    /**
     * 分配角色权限 (先清空后分配)
     * @param roleId
     * @param permissionIds
     */
    @Transactional
    public void setMenusAndPermissions(String roleId,String permissionIds){
        Role role = dao.getById(roleId);
        roleMenuService.clear(role);
        String[] permissions = StrUtil.split(permissionIds, ",");
        for(String pid:permissions){
            if(StrUtil.isEmpty(pid))continue;
            Permission permission = permissionDao.getById(pid);
            Menu menu = permission.getMenu();
            roleMenuService.save(role,menu,permission);
        }
    }
    
    /**
     * 用户角色树
     * @param userId
     * @return
     */
    public List<Role> tree4User(String userId){
        User byId = userDao.getById(userId);
        List<UserRole> userRoles = byId.getUserRoles();
        List<Role> res = new ArrayList<>();
        for(UserRole ur:userRoles){
            res.add(ur.getRole());
        }
        return res;
    }
}
