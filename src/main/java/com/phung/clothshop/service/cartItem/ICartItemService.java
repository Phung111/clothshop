package com.phung.clothshop.service.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.service.IGeneralService;

public interface ICartItemService extends IGeneralService<CartItem, Long> {
    Boolean existsByProduct(Product product);

    Optional<CartItem> findByProductId(@Param("productId") Long productId);
}