package com.gh.ghdg.sysMgr.bean;

import com.gh.ghdg.common.BaseEntity;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class DisplaySeqEntity extends BaseEntity {
    protected Integer displaySeq;
}