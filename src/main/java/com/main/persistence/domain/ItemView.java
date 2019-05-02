package com.main.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "V_ITEM")
@Data
public class ItemView {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SECURE_ID")
    private String secureId;

    @Column(name = "ACTUAL_STOCK")
    private Integer actualStock;

    @Column(name = "RESERVED_STOCK")
    private Integer reservedStock;

    @Column(name = "AVAILABLE_STOCK")
    private Integer availableStock;

    @Column(name = "PRICE")
    private Integer price;

}
