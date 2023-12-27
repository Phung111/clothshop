package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,
        JpaSpecificationExecutor<OrderItem> {

}
