package com.gh.ghdg.oprMgr.entities.notice;

import com.gh.ghdg.sysMgr.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity(name = "sys_notice")
@Table(appliesTo = "sys_notice", comment = "历史消息")
public class Notice extends BaseEntity {
    @Column
    private String senderId;
    @Column
    private String Receiver;
    @Column
    private String tplCode;
    @Column
    private String type;
    @Column
    private String remark;
    @Column
    private Integer status=0;
}
