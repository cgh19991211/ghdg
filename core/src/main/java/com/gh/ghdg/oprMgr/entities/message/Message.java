package com.gh.ghdg.oprMgr.entities.message;

import com.gh.ghdg.sysMgr.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sys_message")
@Table(appliesTo = "sys_message", comment = "历史消息")
public class Message extends BaseEntity {
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
    
    public Message() {
    }
    
    public Message(String senderId, String receiver, String tplCode, String type, String remark, Integer status) {
        this.senderId = senderId;
        Receiver = receiver;
        this.tplCode = tplCode;
        this.type = type;
        this.remark = remark;
        this.status = status;
    }
    
    public String getSenderId() {
        return senderId;
    }
    
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    public String getReceiver() {
        return Receiver;
    }
    
    public void setReceiver(String receiver) {
        Receiver = receiver;
    }
    
    public String getTplCode() {
        return tplCode;
    }
    
    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Notice{" +
                "senderId='" + senderId + '\'' +
                ", Receiver='" + Receiver + '\'' +
                ", tplCode='" + tplCode + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
