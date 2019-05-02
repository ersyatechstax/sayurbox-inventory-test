package com.main.persistence.domain;

import com.main.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ORDERS")
@AllArgsConstructor
@RequiredArgsConstructor
public class Order extends Base{

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYMENT_RECEIVED_DATE")
    private Date paymentReceivedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAYMENT_DUE_DATE")
    private Date paymentDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS",length = 16)
    private OrderStatus orderStatus;

    @Column(name = "ORDER_CODE",nullable = false, unique = true, length = 6)
    private String orderCode;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.orderCode = RandomStringUtils.random(6, true, true);
    }
}
