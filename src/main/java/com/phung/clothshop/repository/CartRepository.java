package com.phung.clothshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.order.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>,
        JpaSpecificationExecutor<Cart> {
}
