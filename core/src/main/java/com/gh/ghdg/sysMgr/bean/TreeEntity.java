package com.gh.ghdg.sysMgr.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.util.List;

@MappedSuperclass
public abstract class TreeEntity<T extends TreeEntity> extends DisplaySeqEntity {
    
    public static final String ROOT_ID = "#";
    
    public static final String PARENT = "parent";

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    protected T parent;
    
    @Transient
    @JsonIgnore
    public abstract T getParent();
    
    public abstract void setParent(T t);
    
    protected List<T> children = Lists.newArrayList();
    
    @JsonIgnore
    @Transient
    public List<T> getChildren0() {
        return children;
    }
    
    // 仅输出到前端用
    @OneToMany(mappedBy = "parent")
    @OrderBy("displaySeq")
    @Fetch(FetchMode.JOIN)
    public List<T> getChildren() {
        return ignoreChildren ? null : children;
    }
    
    // 是否根
    @JsonIgnore
    @Transient
    public boolean isRoot() {
        return ROOT_ID.equals(id);
    }
    
    public static boolean isRoot(TreeEntity t) {
        return t == null || t.isRoot();
    }
    
    // 是否叶子：默认 false
    protected boolean leaf;
    
    // 是否Check：null无口，true打✔，false不打✔
    protected Boolean checked;
    
    // 是否展开
    protected Boolean expanded;

    
    // 忽略子节点
    protected boolean ignoreChildren = true;
    
    @Transient
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
    
    public TreeEntity() {
    }
    
    public TreeEntity(List<T> children, boolean leaf, Boolean checked, Boolean expanded, boolean ignoreChildren) {
        this.children = children;
        this.leaf = leaf;
        this.checked = checked;
        this.expanded = expanded;
        this.ignoreChildren = ignoreChildren;
    }
    
    @Transient
    @JsonIgnore
    public static String getRootId() {
        return ROOT_ID;
    }
    
    @Transient
    @JsonIgnore
    public static String getPARENT() {
        return PARENT;
    }
    
    public void setChildren(List<T> children) {
        this.children = children;
    }
    
    @Transient
    public boolean isLeaf() {
        return leaf;
    }
    
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    @Transient
    public Boolean getChecked() {
        return checked;
    }
    
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
    
    @Transient
    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isIgnoreChildren() {
        return ignoreChildren;
    }
    
    public void setIgnoreChildren(boolean ignoreChildren) {
        this.ignoreChildren = ignoreChildren;
    }
    
    @Override
    public String toString() {
        return "TreeEntity{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                ", displaySeq=" + displaySeq +
                ", children=" + children +
                ", leaf=" + leaf +
                ", checked=" + checked +
                ", expanded=" + expanded +
                ", ignoreChildren=" + ignoreChildren +
                '}';
    }
}
