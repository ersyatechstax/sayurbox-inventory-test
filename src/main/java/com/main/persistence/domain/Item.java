package com.main.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "ITEMS")
public class Item  extends Base{

    private String name;

    private Integer stock;

    private String description;
}
