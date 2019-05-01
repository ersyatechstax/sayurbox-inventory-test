package com.main.persistence.repository;

import com.main.enums.OrderStatus;
import com.main.persistence.domain.Item;
import com.main.persistence.domain.Order;
import com.main.persistence.domain.OrderDetail;
import com.main.persistence.domain.User;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends BaseRepository<OrderDetail> {
    Optional<OrderDetail> findByOrderAndItem(Order order, Item item);

    List<OrderDetail> findByOrder(Order order);

    List<OrderDetail> findByOrderIn(List<Order> order);

    Optional<OrderDetail> findByOrderUserAndOrderOrderStatusAndItemSecureId(User user, OrderStatus orderStatus, String itemId);

    List<OrderDetail> findByOrderUserAndOrderOrderStatus(User user, OrderStatus orderStatus);
}
