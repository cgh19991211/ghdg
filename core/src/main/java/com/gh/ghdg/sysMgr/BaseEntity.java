package com.gh.ghdg.sysMgr;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"}) // 序列化时忽略，防止jpa懒加载异常(hibernate Proxy无法被序列化)
public abstract class BaseEntity implements Serializable {
    
    protected String id;
    
    protected User createdBy;
    
    protected Date createdDate;
    
    protected User lastModifiedBy;
    
    protected Timestamp lastModifiedDate;
    
    protected Timestamp lastModifiedDate0;
    
    public BaseEntity() {
    }
    
    public BaseEntity(String id, User createdBy, Date createdDate, User lastModifiedBy, Timestamp lastModifiedDate, Timestamp lastModifiedDate0) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedDate0 = lastModifiedDate0;
    }
    
    @Id
    @GenericGenerator(name = "myid", strategy = "com.gh.ghdg.sysMgr.bean.entities.IdGenerator")
    @GeneratedValue(generator = "myid")
    @Length(max = 32, message = "ID 不得超过 32 位")
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @CreatedBy
    @JsonIgnore // Json 忽略
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false,columnDefinition = "string COMMENT '创建人'")
    @JSONField(serialize = false)
    public User getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_date", columnDefinition = "DATETIME COMMENT '创建时间/注册时间'")
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    @LastModifiedBy
    @JsonIgnore // Json 忽略
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by",columnDefinition = "string COMMENT '最后更新时间'")
    @JSONField(serialize = false) // JSON.toJSONString 时忽略
    public User getLastModifiedBy() {
        return lastModifiedBy;
    }
    
    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Column(name = "last_modified_date", columnDefinition = "DATETIME COMMENT '最后更新人'")
    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }
    
    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    // 判断时间戳用
    @Transient // 瞬时（不持久化）
    @JsonIgnore
    @JSONField(serialize = false)
    public Timestamp getLastModifiedDate0() {
        return lastModifiedDate0;
    }
    
    public void setLastModifiedDate0(Timestamp lastModifiedDate0) {
        this.lastModifiedDate0 = lastModifiedDate0;
    }
    
    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy=" + lastModifiedBy +
                ", lastModifiedDate=" + lastModifiedDate +
                ", lastModifiedDate0=" + lastModifiedDate0 +
                '}';
    }
}
