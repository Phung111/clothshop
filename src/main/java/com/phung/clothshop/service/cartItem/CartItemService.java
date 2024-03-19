package com.phung.clothshop.service.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.repository.CartItemRepository;

@Service
@Transactional
public class CartItemService implements ICartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getById(Long t) {
        return cartItemRepository.getById(t);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem save(CartItem e) {
        return cartItemRepository.save(e);
    }

    @Override
    public void delete(CartItem e) {
        e.setDeleted(true);
        cartItemRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public Boolean existsByProduct(Product product) {
        return cartItemRepository.existsByProduct(product);
    }

    @Override
    public Optional<CartItem> findByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }
}
