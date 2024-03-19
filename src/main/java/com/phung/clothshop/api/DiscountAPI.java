package com.phung.clothshop.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.discount.DiscountCreateReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.service.discount.IDiscountService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.utils.AppUtils;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/discount")
public class DiscountAPI {

    @Autowired
    private IDiscountService iDiscountService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createDiscount(
            @RequestBody DiscountCreateReqDTO discountCreateReqDTO,
            HttpServletRequest request,
            BindingResult bindingResult) throws ParseException {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        List<ProductResDTO> productResDTOs = iDiscountService.createDiscount(discountCreateReqDTO);

        return new ResponseEntity<>(productResDTOs, HttpStatus.OK);
    }

    @PostMapping("/checkDate")
    public ResponseEntity<?> runCheckDate() {
        List<ProductResDTO> productResDTOs = checkDate();
        return new ResponseEntity<>(productResDTOs, HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public List<ProductResDTO> checkDate() {
        List<Discount> discounts = iDiscountService.findExpiredDiscounts();
        List<ProductResDTO> productResDTOs = new ArrayList<>();
        for (Discount discount : discounts) {
            List<Product> products = iProductService.findByDiscountId(discount.getId());
            for (Product product : products) {
                product.setPriceTotal(product.getPrice());
                product.setDiscount(null);
                product = iProductService.save(product);
                productResDTOs.add(product.toProductResDTO());
            }
        }
        return productResDTOs;
    }

}
