package com.phung.clothshop.api;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.domain.dto.order.OrderPageReqDTO;
import com.phung.clothshop.domain.dto.order.OrderReqDTO;
import com.phung.clothshop.domain.dto.order.OrderResDTO;
import com.phung.clothshop.service.IClothShopService;
import com.phung.clothshop.service.JwtService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.service.order.IOrderService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {

    @Autowired
    private IClothShopService iClothShopService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/placeOrder")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> placeOrder(@Validated OrderReqDTO orderReqDTO,
            HttpServletRequest request) {

        OrderResDTO orderResDTO = iClothShopService.placeOrder(orderReqDTO, request);

        return new ResponseEntity<>(orderResDTO, HttpStatus.OK);
    }

    @PostMapping("/get-order")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getOrder(OrderPageReqDTO orderPageReqDTO, HttpServletRequest request) {

        String sizeStr = orderPageReqDTO.getSize();
        Integer size = (sizeStr != null && !sizeStr.trim().isEmpty()) ? Integer.parseInt(sizeStr) : 15;

        String currentPageStr = orderPageReqDTO.getPage();
        Integer currentPage = (currentPageStr != null && !currentPageStr.trim().isEmpty()) ? Integer.parseInt(currentPageStr) - 1 : 0;

        Pageable pageable = PageRequest.of(currentPage, size);

        Page<OrderResDTO> orderResDTOs = iOrderService.getOrder(pageable);

        return new ResponseEntity<>(orderResDTOs,HttpStatus.OK);
    }

    @PostMapping("/get-order-by-customer")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getOrderByCustomer(OrderPageReqDTO orderPageReqDTO, HttpServletRequest request) {

        String sizeStr = orderPageReqDTO.getSize();
        Integer size = (sizeStr != null && !sizeStr.trim().isEmpty()) ? Integer.parseInt(sizeStr) : 15;

        String currentPageStr = orderPageReqDTO.getPage();
        Integer currentPage = (currentPageStr != null && !currentPageStr.trim().isEmpty()) ? Integer.parseInt(currentPageStr) - 1 : 0;

        Pageable pageable = PageRequest.of(currentPage, size);

        Page<OrderResDTO> orderResDTOs = iOrderService.getOrderByCustomer(pageable, request);

        return new ResponseEntity<>(orderResDTOs,HttpStatus.OK);
    }

    @PostMapping("/get-order/{orderID}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getOrderByID(@PathVariable Long orderID) {

        Optional<Order> orderOptional = iOrderService.findById(orderID);
        if (!orderOptional.isPresent()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Order not found with ID: " + orderID);
        }
        OrderResDTO orderResDTO = orderOptional.get().toOrderResDTO();

        return new ResponseEntity<>(orderResDTO,HttpStatus.OK);
    }
}
