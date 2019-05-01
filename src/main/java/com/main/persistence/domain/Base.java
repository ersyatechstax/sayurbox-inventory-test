package com.main.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * created by ersya 30/03/2019
 */
@MappedSuperclass
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Data

public abstract class Base implements Serializable {

    private static final long serialVersionUID = -7369920601847524273L;

    public Base() {
    }

    @Id
    @GeneratedValue
    protected Integer id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version = 0;

    @Column(name = "SECURE_ID", unique = true, nullable = false, columnDefinition = "char(36)")
    private String secureId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", updatable = false, nullable = false)
    private Date createdDate;

    @CreatedBy
    @Column(name = "CREATED_BY", length = 30, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_MODIFIED")
    private Date modifiedDate;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", length = 30)
    private String modifiedBy;

    @PrePersist
    public void prePersist(){
        this.secureId = UUID.randomUUID().toString();
        this.createdDate = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        this.modifiedDate = new Date();
    }
}
