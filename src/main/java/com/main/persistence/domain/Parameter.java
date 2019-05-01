package com.main.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "PARAMETER",
        indexes = {
                @Index(columnList = "CODE", name = "UK_CODE", unique = true)
        })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "project")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Parameter extends Base{
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String value;

    private String description;
}
