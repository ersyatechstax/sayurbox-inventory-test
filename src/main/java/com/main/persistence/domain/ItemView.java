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

    private Integer version;

    private String name;

    private String description;

    private String secureId;

    private Integer actualStock;

    private Integer reservedStock;

    private Integer availableStock;

    private Integer price;

}
