package com.gh.ghdg.sysMgr.bean;

import com.gh.ghdg.sysMgr.BaseEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DisplaySeqEntity extends BaseEntity {
    protected Integer displaySeq;
    
    public DisplaySeqEntity() {
    }
    
    public DisplaySeqEntity(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }
    
    public Integer getDisplaySeq() {
        return displaySeq;
    }
    
    public void setDisplaySeq(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }
    
    @Override
    public String toString() {
        return "DisplaySeqEntity{" +
                "displaySeq=" + displaySeq +
                '}';
    }
}
