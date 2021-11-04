package com.gh.ghdg.sysMgr.core.service.system;

import com.gh.ghdg.sysMgr.bean.entities.system.Module;
import com.gh.ghdg.sysMgr.core.dao.system.ModuleDao;
import com.gh.ghdg.sysMgr.core.service.TreeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ModuleService extends TreeService<Module, ModuleDao> {
    /**
     * 保存
     * @param t
     * @return
     * @throws Exception
     */
    @Transactional
    public Module save(Module t) throws Exception {
        return super.save(t);
    }
    
    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @Transactional
    public List<Module> delete(Module t) {
        return super.delete(t);
    }
}
