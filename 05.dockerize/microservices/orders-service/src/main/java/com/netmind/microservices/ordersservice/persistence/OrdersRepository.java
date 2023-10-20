package com.netmind.microservices.ordersservice.persistence;

import com.netmind.microservices.ordersservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}
