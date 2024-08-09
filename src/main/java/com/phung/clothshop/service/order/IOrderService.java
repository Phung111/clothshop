package com.phung.clothshop.service.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.phung.clothshop.domain.dto.order.OrderPageReqDTO;
import com.phung.clothshop.domain.dto.order.OrderResDTO;
import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.service.IGeneralService;

public interface IOrderService extends IGeneralService<Order, Long> {

    Page<OrderResDTO> getOrder(Pageable pageable,HttpServletRequest request);

    Page<OrderResDTO> getOrderByCustomer(Pageable pageable, Long customerID);

}
