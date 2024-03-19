package com.phung.clothshop.service.order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Bill;
import com.phung.clothshop.domain.entity.order.CartItem;
import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.domain.entity.order.OrderItem;
import com.phung.clothshop.domain.entity.order.Ship;
import com.phung.clothshop.domain.entity.product.EProductStatus;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.repository.OrderRepository;
import com.phung.clothshop.service.bill.IBillService;
import com.phung.clothshop.service.cart.ICartService;
import com.phung.clothshop.service.cartItem.ICartItemService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.service.orderitem.IOrderItemService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.service.ship.IShipService;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private ICartItemService iCartItemService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IShipService iShipService;

    @Autowired
    private IBillService iBillService;

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
