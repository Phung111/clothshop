package com.phung.clothshop.domain.dto.clothshop;

import java.util.List;

import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.banner.Banner;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClothshopRes {

    private List<Banner> banners;

    private List<ProductResDTO> discounts;

    private List<ProductResDTO> sales;

    private List<String> categories;

    private List<ProductResDTO> products;
}
