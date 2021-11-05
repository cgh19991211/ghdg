package com.gh.ghdg.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gh.ghdg.sysMgr.bean.entities.system.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"}) // 解决懒加载异常
public abstract class BaseEntity implements Serializable {
    @Id
    @GenericGenerator(name = "myid", strategy = "com.gh.ghdg.sysMgr.bean.entities.IdGenerator")
    @GeneratedValue(generator = "myid")
    @Length(max = 32, message = "ID 不得超过 32 位")
    protected String id;
    
    @CreatedBy
    @JsonIgnore // Json 忽略
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false,columnDefinition = "string COMMENT '创建人'")
    @JSONField(serialize = false)
    protected User createdBy;
    
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "created_date", columnDefinition = "DATETIME COMMENT '创建时间/注册时间'")
    protected Date createdDate;
    
    @LastModifiedBy
    @JsonIgnore // Json 忽略
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by",columnDefinition = "string COMMENT '最后更新时间'")
    @JSONField(serialize = false) // JSON.toJSONString 时忽略
    protected User lastModifiedBy;
    
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @Column(name = "last_modified_date", columnDefinition = "DATETIME COMMENT '最后更新人'")
    protected Timestamp lastModifiedDate;
    
    // 判断时间戳用
    @Transient // 瞬时（不持久化）
    @JsonIgnore
    @JSONField(serialize = false)
    protected Timestamp lastModifiedDate0;
    

    
}
