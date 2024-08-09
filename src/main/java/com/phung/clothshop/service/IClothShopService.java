package com.phung.clothshop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.phung.clothshop.domain.dto.order.OrderReqDTO;
import com.phung.clothshop.domain.dto.order.OrderResDTO;

public interface IClothShopService {
    OrderResDTO placeOrder(OrderReqDTO orderReqDTO, HttpServletRequest request);
}
