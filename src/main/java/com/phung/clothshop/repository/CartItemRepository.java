package com.phung.clothshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.product.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        JpaSpecificationExecutor<CartItem> {
    Boolean existsByProduct(Product product);

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id = :productId")
    Optional<CartItem> findByProductId(@Param("productId") Long productId);

}
