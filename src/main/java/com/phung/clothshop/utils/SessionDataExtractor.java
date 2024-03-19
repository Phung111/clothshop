package com.phung.clothshop.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.phung.clothshop.exceptions.CustomErrorException;

@Component
public class SessionDataExtractor {

    public Long extractCustomerID(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Session not found");
        }
        Long customerID = (Long) session.getAttribute("CUSTOMER_ID");
        if (customerID == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "CUSTOMER_ID not found in session");
        }
        return customerID;
    }

    public Long extractCartID(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Session not found");
        }
        Long cartID = (Long) session.getAttribute("CART_ID");
        if (cartID == null) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "CART_ID not found in session");
        }
        return cartID;
    }
}
