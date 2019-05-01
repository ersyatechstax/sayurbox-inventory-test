package com.main.persistence.domain;

import com.main.persistence.domain.Item;
import com.main.persistence.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDetail extends Base{
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private Integer quantity;

    private String note;

}
