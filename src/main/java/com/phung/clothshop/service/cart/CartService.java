package com.phung.clothshop.service.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.entity.order.Cart;
import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.repository.CartRepository;

@Service
@Transactional
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(Long t) {
        return cartRepository.getById(t);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart e) {
        return cartRepository.save(e);
    }

    @Override
    public void delete(Cart e) {
        e.setDeleted(true);
        cartRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

}
