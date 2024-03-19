package com.phung.clothshop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.phung.clothshop.domain.dto.order.BillDTO;
import com.phung.clothshop.domain.dto.order.BillResDTO;
import com.phung.clothshop.domain.dto.order.OrderReqDTO;

public interface IClothShopService {
    BillResDTO placeOrder(OrderReqDTO orderReqDTO, HttpServletRequest request);
}
