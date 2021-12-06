package com.gh.ghdg.sysMgr.core.service;

import com.gh.ghdg.common.commonVo.DynamicSpecifications;
import com.gh.ghdg.common.commonVo.SearchFilter;
import com.gh.ghdg.sysMgr.bean.TreeEntity;
import com.gh.ghdg.sysMgr.core.dao.TreeDao;
import com.gh.ghdg.common.utils.exception.MyException;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public abstract class TreeService<T extends TreeEntity<T>, S extends TreeDao<T>> extends DisplaySeqService<T, S> {

    public static final String APPEND = "append";
    public static final String AFTER = "after";
    public static final String BEFORE = "before";

    /**
     * 保存：当父结点为根（id为#）时，置空
     * @param t
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public T save(T t) throws Exception {
        T p = t.getParent();
        if (null != p && TreeEntity.ROOT_ID.equals(p.getId())) {
            t.setParent(null);
        }

        t = super.save(t);

        t.setIgnoreChildren(false); // 新增保存，不忽略子节点，返回 []
        return t;
    }

    /**
     * 移动
     * @param t
     * @param overId
     * @param position append after before
     * @return
     * @throws Exception
     */
    @Transactional
    public List<T> move(T t, String overId, String position) {
        switch (position) {
            case APPEND: // append
                return append(t, overId);
            default: // after before
                return afterOrBefore(t, overId, position);
        }
    }

    /**
     * 追加
     * @param t
     * @param overId
     * @return
     * @throws Exception
     */
    @Transactional
    protected List<T> append(T t, String overId) {
        T p0 = t.getParent(); // 原父亲
        T p = getOver(overId); // 新父亲
        boolean changeParent = isChangeParent(p0, p); // 是否换父亲

        List<T> result;
        // 换父亲
        if (changeParent) {
            result = Lists.newArrayList();
            // 新兄弟
            List<T> newSiblings = getSortedChildren(p, t);
            newSiblings.add(t);
            List<T> newChanges = dealDisplaySeqs(newSiblings);
            result.addAll(newChanges);

            // 旧兄弟
            List<T> oldSiblings = getSortedChildren(p0, t);
            oldSiblings.remove(t);
            List<T> oldChanges = dealDisplaySeqs(oldSiblings);
            result.addAll(oldChanges);

            // 自己
            t.setParent(p);
            dao.save(t);
            if(!result.contains(t)) { // 新兄弟可能包含了自己 by hqw 2020-01-10
                result.add(t); // 加入返回
            }
        } else { // 不换父亲
            // 兄弟
            List<T> siblings = getSortedChildren(p0, t);
            List<T> news = Lists.newArrayList();
            for (T s : siblings) {
                if (!s.equals(t)) {
                    news.add(s);
                }
            }
            news.add(t);
            result = dealDisplaySeqs(news);
        }
        return result;
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
    protected List<T> afterOrBefore(T t, String overId, String position) {
        boolean after = AFTER.equals(position); // after before
        T p0 = t.getParent(); // 原父亲
        T over = getOver(overId); // To
        T p = over.getParent(); // 新父亲
        boolean changeParent = isChangeParent(p0, p); // 是否换父亲

        List<T> result;
        // 换父亲
        if (changeParent) {
            result = Lists.newArrayList();
            // 新兄弟
            List<T> newSiblings = getSortedChildren(p, t);
            List<T> news = Lists.newArrayList();
            for (T s : newSiblings) {
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
            List<T> newResult = dealDisplaySeqs(news);
            result.addAll(newResult);

            // 旧兄弟
            List<T> oldSiblings = getSortedChildren(p0, t);
            oldSiblings.remove(t);
            List<T> oldResult = dealDisplaySeqs(oldSiblings);
            result.addAll(oldResult);

            // 自己
            t.setParent(p);
            dao.save(t);
            result.add(t); // 加入返回
        } else { // 不换父亲
            // 兄弟
            List<T> siblings = getSortedChildren(p0, t);
            // 移动
            List<T> news = afterOrBefore(t, over, after, siblings);

            result = dealDisplaySeqs(news);
        }
        return result;
    }

    /**
     * 删除
     * @param t
     * @return
     */
    @Override
    @Transactional
    public List<T> delete(T t) {
        // 检查下级
        if(t.getChildren0().size() > 0) {
            throw new MyException("存在下级，不能删除");
        }

        // 删除
        /*super*/dao.delete(t);
        // 父级 by hqw 2020-01-19
        T p = t.getParent();
        if(null != p) {
            p.getChildren0().remove(t);
        }

        // 处理排序
        List<T> siblings = getSortedSiblings(t);
        List<T> result = dealDisplaySeqs(siblings);

        return result;
    }

    /**
     * 完整树
     * @return
     */
    public List<T> tree() {
        return getRoots(null);
    }

    /**
     * 异步树
     * @param t
     * @return
     */
    public List<T> tree(T t) {
        return tree(t, false);
    }

    /**
     * 生效异步树
     * @param t
     * @return
     */
    public List<T> okTree(T t) {
        return tree(t, true);
    }

    /**
     * 异步处理
     * @param ts
     */
    protected void async(List<T> ts) {
        for(T t : ts) {
            async(t);
        }
    }

    /**
     * 异步处理
     * @param t
     */
    protected void async(T t) {
        t.setIgnoreChildren(false); // 异步树不忽略子节点（null/[]）
        if(t.getChildren0().size() > 0) {
            t.setChildren(null);
        }
    }

    /**
     * 异步树
     * @param t
     * @param ok
     * @return
     */
    private List<T> tree(T t, boolean ok) {
        SearchFilter eq = t.getParent().isRoot() ? SearchFilter.build("parent",SearchFilter.Operator.ISNULL):
                                SearchFilter.build("parent",SearchFilter.Operator.EQ,t);
        List<SearchFilter> sf = new ArrayList<>();
        sf.add(eq);
        Specification spec = DynamicSpecifications.bySearchFilter(sf,t.getClass());

        List<T> ts = dao.findAll(spec, Sort.by(Sort.Order.asc("seq")));
        async(ts);
        return ts;
    }


    /**
     * 递归排序
     * @param ts
     */
    protected void recurSort(List<T> ts) {
        sort(ts);
        for(T t : ts) {
            recurSort(t.getChildren0());
        }
    }

    /**
     * 取排序
     * @param t
     * @return
     */
    @Override
    protected void getDisplaySeq(T t) {
        T p = t.getParent();
        if(null != p) {
            p = dao.getOne(p.getId());
            t.setParent(p);
        }

        List<T> siblings = getSortedSiblings(t);
        int seq = siblings.size() + 1;
        t.setDisplaySeq(seq);
    }

    /**
     * 取得所有兄弟
     * @param t
     * @return
     */
    private List<T> getSortedSiblings(T t) {
        return getSortedChildren(t.getParent(), t);
    }

    /**
     * 取根
     * @param t
     * @return
     */
    protected List<T> getRoots(T t) {
        SearchFilter parent = SearchFilter.build("parent", SearchFilter.Operator.ISNULL);
        
        return super.queryAll(parent, Sort.by(Sort.Order.asc("displaySeq")));
    }

    /**
     * 取得排序的所有儿子
     * @param p
     * @param t
     * @return
     */
    private List<T> getSortedChildren(T p, T t) {
        List<T> children;
        if (null == p) {
            children = getRoots(t);
        } else {
            children = p.getChildren0();
        }
        sort(children); // 排序
        return children;
    }

    /**
     * 判断换父亲
     * @param p0
     * @param p
     * @return
     */
    private boolean isChangeParent(T p0, T p) {
        if (null == p0 && null == p) {
            return false;
        }
        if (null != p0 && null == p) {
            return true;
        }
        if (null == p0 && null != p) {
            return true;
        }
        return !p0.getId().equals(p.getId()); // 这里 id 必不空
    }

    /**
     * 组成树
     * @param ts
     * @return
     */
    protected List<T> asTreeCheckable(List<T> ts) {
        checkable(ts);
        return asTree(ts);
    }

    /**
     * 组成树
     * @param ts
     * @return
     */
    protected List<T> asTree(List<T> ts) {
        List<T> roots = Lists.newArrayList();
        List<T> list = Lists.newArrayList();

        for(T t : ts) {
            // children
            t.setChildren(Lists.newArrayList());
            list.add(t);
        }

        for(T t : ts) {
            // 列表
            T p = t.getParent();
            while(null != p) {
                if(!list.contains(p)) {
                    if(!p.getChildren0().isEmpty()) {
                        p.setChildren(Lists.newArrayList());
                    }
                    list.add(p);
                }
                if(!p.getChildren0().contains(t)) { // by hqw 2020-01-06，解决菜单问题
                    p.getChildren0().add(t);
                }
                t = p;
                p = t.getParent();
            }
            // 根
            if(!roots.contains(t)) {
                roots.add(t);
            }
        }

        return roots;
    }

    /**
     * 带口
     * @param ts
     */
    protected void checkable(List<T> ts) {
        for(T t : ts) {
            t.setChecked(false);
        }
    }


}
