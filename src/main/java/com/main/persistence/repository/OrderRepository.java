package com.main.persistence.repository;

import com.main.enums.OrderStatus;
import com.main.persistence.domain.Order;
import com.main.persistence.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<Order> {
    Optional<Order> findByUserAndOrderStatus(User user, OrderStatus orderStatus);

    Page<Order> findByUserAndOrderStatusIn(User user, List<OrderStatus> orderStatuses, Pageable pageable);
}
