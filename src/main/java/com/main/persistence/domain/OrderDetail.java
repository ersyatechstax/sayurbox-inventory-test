package com.main.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDetail extends Base{
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "NOTE")
    private String note;

}
