package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.order.Bill;
import com.phung.clothshop.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
                JpaSpecificationExecutor<Order> {

}
