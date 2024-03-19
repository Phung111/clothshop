package com.phung.clothshop.service.discount;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import com.phung.clothshop.domain.dto.discount.DiscountCreateReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.service.IGeneralService;

public interface IDiscountService extends IGeneralService<Discount, Long> {

    List<ProductResDTO> createDiscount(DiscountCreateReqDTO discountCreateReqDTO) throws ParseException;

    List<Discount> findExpiredDiscounts();
}
