package com.gh.ghdg.sysMgr.core.service;

import cn.hutool.core.util.StrUtil;
import com.gh.ghdg.sysMgr.BaseService;
import com.gh.ghdg.sysMgr.bean.DisplaySeqEntity;
import com.gh.ghdg.sysMgr.core.dao.DisplaySeqDao;
import com.gh.ghdg.common.utils.exception.MyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class DisplaySeqService<T extends DisplaySeqEntity, S extends DisplaySeqDao<T>> extends BaseService<T, S> {

    @Override
    public T save(T t) throws Exception {
        if(StrUtil.isEmptyIfStr(t.getId())) {
            getDisplaySeq(t);
        }
        return super.save(t);
    }

    /**
     * 移动
     * @param t
     * @param overId
     * @param position append after before
     * @return
     */
    @Transactional
    public List<T> move(T t, String overId, String position) throws Exception {
        return afterOrBefore(t, overId, position);
    }

    /**
     * 删除
     * @param t
     * @return
     */
    @Transactional
    @Override
    public List<T> delete(T t) throws Exception {
        super.delete(t);
        // 排序
        List<T> ts = list(t);
        sort(ts);
        return dealDisplaySeqs(ts);
    }
    
    /**
     * 列表
     * @param spec
     * @return
     */
    public List<T> list(Specification spec) {
        return list(spec, null);
    }
    
    /**
     * 列表
     * @param spec
     * @param sort
     * @return
     */
    public List<T> list(Specification spec, Sort sort) {
        return sort != null ? dao.findAll(spec, sort) : dao.findAll(spec);
    }
    
    public List<T> list(T t) throws Exception {
        return list(t, null);
    }
    
    public List<T> list(T t,Specification spec){
        Sort sort = Sort.by("displaySeq");
        List<T> res = dao.findAll(sort);
        return res;
    }
    
    /**
     * 取排序
     * @param t
     * @return
     */
    protected void getDisplaySeq(T t) throws Exception {
        List<T> siblings = list(t);
        int seq = siblings.size() + 1;
        t.setDisplaySeq(seq);
    }

    /**
     * 置后/前
     * @param t
     * @param overId
     * @param position
     * @return
     * @throws Exception
     */
    @Transactional
    protected List<T> afterOrBefore(T t, String overId, String position) throws Exception {
        boolean after = "after".equals(position); // after before
        T over = getOver(overId);
        List<T> ts = list(t);
        sort(ts);

        // 移动
        List<T> news = afterOrBefore(t, over, after, ts);

        List<T> result = dealDisplaySeqs(news);
        return result;
    }
    
    /**
     * 移动
     * @param t
     * @param over
     * @param after
     * @param ts
     * @return
     */
    protected List<T> afterOrBefore(T t, T over, boolean after, List<T> ts) {
        List<T> news = new ArrayList<>();
        for (T s : ts) {
            if (!s.equals(t)) { // 不是自己
                if (after) {
                    news.add(s); // after，t 后加
                }
                if (s.equals(over)) {
                    news.add(t);
                }
                if (!after) {
                    news.add(s); // before，t 先加
                }
            }
        }
        return news;
    }

    /**
     * 取到 overId 对应的结点
     * @param overId
     * @return
     */
    protected T getOver(String overId) {
        T to;
        if ("#".equals(overId)) {
            to = null;
        } else {
            Optional<T> opt = dao.findById(overId);
            if (!opt.isPresent()) {
                throw new MyException("ID 为 " + overId + " 的行不存在");
            }
            to = opt.get();
        }
        return to;
    }

    /**
     * 排序
     * @param ts
     */
    public void sort(List<T> ts) {
        ts.sort(Comparator.comparing(T::getDisplaySeq));
    }

    /**
     * 处理排序：移走结点后，处理剩余兄弟的 displaySeq
     * @param toDeals
     * @return
     */
    @Transactional
    protected List<T> dealDisplaySeqs(List<T> toDeals) {
        List<T> result = new ArrayList<>();
        int i = 1;
        for (T s : toDeals) {
            if (i != s.getDisplaySeq()) {
                s.setDisplaySeq(i);
                dao.save(s);
                result.add(s); // 加入返回
            }
            ++i;
        }
        return result;
    }

}
