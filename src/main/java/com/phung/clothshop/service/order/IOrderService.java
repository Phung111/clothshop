package com.phung.clothshop.service.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.phung.clothshop.domain.entity.order.Order;
import com.phung.clothshop.service.IGeneralService;

public interface IOrderService extends IGeneralService<Order, Long> {

}
