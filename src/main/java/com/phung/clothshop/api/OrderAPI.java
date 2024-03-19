package com.phung.clothshop.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.phung.clothshop.domain.dto.order.BillDTO;
import com.phung.clothshop.domain.dto.order.BillResDTO;
import com.phung.clothshop.domain.dto.order.OrderReqDTO;
import com.phung.clothshop.service.IClothShopService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {

    @Autowired
    private IClothShopService iClothShopService;

    @PostMapping("/place")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> place(@RequestBody OrderReqDTO orderReqDTO,
            HttpServletRequest request) {

        BillResDTO billResDTO = iClothShopService.placeOrder(orderReqDTO, request);

        return new ResponseEntity<>(billResDTO, HttpStatus.OK);
    }
}
