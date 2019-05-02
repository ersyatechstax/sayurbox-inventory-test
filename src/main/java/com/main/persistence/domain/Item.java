package com.main.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "ITEMS")
public class Item  extends Base{

    @Column(name = "NAME")
    private String name;

    @Column(name = "STOCK")
    private Integer stock;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Integer price;
}
