package com.gh.ghdg.sysMgr.core.service.system;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.common.cache.redis.RedisUtil;
import com.gh.ghdg.common.utils.constant.Constants;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.bean.entities.system.*;
import com.gh.ghdg.sysMgr.bean.enums.TypeEnum;
import com.gh.ghdg.sysMgr.core.dao.system.*;
import com.gh.ghdg.sysMgr.core.service.TreeService;
import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.security.JwtUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService extends TreeService<Menu, MenuDao> {
    
    @Autowired
    private UserMenuDao userMenuDao;
    
    @Autowired
    private RoleDao roleDao;
    
    
    /**
     * 保存
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public Menu save(Menu t) throws Exception{
        return super.save(t);
    }
    
    /**
     * 删除菜单(未分配权限与角色)
     * 且同时删除中间表UserMenu所关联的行(手动维护表的关联关系)
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public List<Menu> delete(Menu t) {
        if(t.getPermission()!=null){
            throw new MyException("存在权限未删除");
        }
        if(t.getRoleMenus().size()>0){
            throw new MyException("已分配角色");
        }
        for(UserMenu um : t.getUserMenus()) {
            userMenuDao.delete(um);
        }
        return super.delete(t);
    }
    
    /**
     * 返回树根
     * @return
     */
    public List<Menu> tree(){
        List<Menu> tree = super.tree();
//        clearPerms(tree);
        return tree;
    }
    
    /**
     * 角色的菜单树
     * @param roleId
     * @return
     */
    public List<Menu> tree4Role(String roleId) {
        if(roleId==null|| TreeEntity.ROOT_ID.equals(roleId)) {
            return null;
        }

        List<Menu> selected = Lists.newArrayList();
        Role r = roleDao.getById(roleId);
        for(RoleMenu rm : r.getRoleMenus()) {
            Menu m = rm.getMenu();

            Permission perms = null;
            for(RoleMenuPermission rmp : rm.getRoleMenuPermissions()) {
                perms= rmp.getPermission();
            }
//            permissionService.sort(perms);
            m.setAuthPermission(perms);

            selected.add(m);
        }

        List<Menu> roots = asTreeCheckable(selected);
        recurSort(roots);
        return roots;
    }


}
