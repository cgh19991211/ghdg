package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.common.utils.exception.MyException;
import com.gh.ghdg.sysMgr.bean.entities.system.Permission;
import com.gh.ghdg.sysMgr.core.dao.system.PermissionDao;
import com.gh.ghdg.sysMgr.core.service.DisplaySeqService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PermissionService extends DisplaySeqService<Permission, PermissionDao> {
    
    /**
     * 保存
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public Permission save(Permission t) throws Exception {
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @Transactional
    public List<Permission> delete(Permission t) throws Exception {
        if(t.getRoleMenuPermissions().size() > 0) {
            throw new MyException("已分配角色");
        }
        return super.delete(t);
    }
    
    
    /**
     * 权限列表
     * @param t
     * @return
     */
    @Override
    public List<Permission> list(Permission t) throws Exception {
        return super.list(t);
    }

}
