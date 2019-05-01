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
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentReceivedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private OrderStatus orderStatus;

    @Column(nullable = false, unique = true, length = 6)
    private String orderCode;

    private Integer totalPrice;

    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.orderCode = RandomStringUtils.random(6, true, true);
    }
}
