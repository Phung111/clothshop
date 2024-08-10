package com.phung.clothshop.service.order;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.dto.order.OrderResDTO;
import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.repository.OrderRepository;
import com.phung.clothshop.service.JwtService;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JwtService jwtService;

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

    @Override
    public Page<OrderResDTO> getOrder(Pageable pageable) {
        return orderRepository.getOrder(pageable).map(Order::toOrderResDTO);
    }

    @Override
    public Page<OrderResDTO> getOrderByCustomer(Pageable pageable,HttpServletRequest request) {
        String jwtToken = jwtService.extractJwtFromRequest(request);
        String role = jwtService.getRoleFromJwtToken(jwtToken);
        Long customerID = jwtService.getCustomerIdFromJwtToken(jwtToken);

        return orderRepository.getOrderByCustomer(pageable, customerID).map(Order::toOrderResDTO);
      
    }

}
