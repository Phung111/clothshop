package com.phung.clothshop.service.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.model.order.Order;
import com.phung.clothshop.repository.OrderRepository;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long t) {
        return orderRepository.getById(t);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order e) {
        return orderRepository.save(e);
    }

    @Override
    public void delete(Order e) {
        e.setDeleted(true);
        orderRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

}
