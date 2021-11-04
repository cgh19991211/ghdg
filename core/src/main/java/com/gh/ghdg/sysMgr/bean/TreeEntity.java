package com.gh.ghdg.sysMgr.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.util.List;

@Data
@MappedSuperclass
public abstract class TreeEntity<T extends TreeEntity> extends DisplaySeqEntity {
    
    @Transient
    @JsonIgnore
    public static final String ROOT_ID = "#";
    
    @Transient
    @JsonIgnore
    public static final String PARENT = "parent";

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    protected T parent;
    
    public abstract T getParent();
    
    public abstract void setParent(T t);
    
    @OneToMany(mappedBy = "parent")
    @OrderBy("displaySeq")
    protected List<T> children = Lists.newArrayList();
    
    @JsonIgnore
    public List<T> getChildren0() {
        return children;
    }
    
    // 仅输出到前端用
    public List<T> getChildren() {
        return ignoreChildren ? null : children;
    }
    
    // 是否根
    @JsonIgnore
    public boolean isRoot() {
        return ROOT_ID.equals(id);
    }
    
    public static boolean isRoot(TreeEntity t) {
        return t == null || t.isRoot();
    }
    
    // 是否叶子：默认 false
    @Transient
    protected boolean leaf;
    
    // 是否Check：null无口，true打✔，false不打✔
    @Transient
    protected Boolean checked;
    
    // 是否展开
    @Transient
    protected Boolean expanded;

    
    // 忽略子节点
    @Transient
    @JsonIgnore
    @JSONField(serialize = false)
    protected boolean ignoreChildren = true;
    
    public Boolean getExpanded() {
        // 指定 expanded，返回 expanded
        if (expanded != null) {
            return expanded;
        }
        return children != null && children.isEmpty();
        // getChildren() != null && getChildren().isEmpty(); // 不用 children，可能被重写
    }
    
    public List<T> withDescendants() {
        return recur((T) this);
    }
    
    
    private List<T> recur(T t) {
        List<T> ts = Lists.newArrayList(t);
        List<T> children = t.getChildren0();
        for (T c : children) {
            ts.addAll(recur(c));
        }
        return ts;
    }
}
