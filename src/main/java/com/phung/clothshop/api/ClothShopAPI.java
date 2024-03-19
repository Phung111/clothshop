package com.phung.clothshop.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.discount.DiscountCreateReqDTO;
import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.banner.Banner;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.ECategory;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.IClothShopService;
import com.phung.clothshop.service.banner.IBannerService;
import com.phung.clothshop.service.discount.IDiscountService;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.EnumToString;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clothshop")
public class ClothShopAPI {

    @Autowired
    private IClothShopService iClothShopService;

    @Autowired
    private IDiscountService iDiscountService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IBannerService iBannerService;

    @Autowired
    private EnumToString enumToString;

    @GetMapping("")
    public ResponseEntity<?> getAll(ProductPageReqDTO productPageReqDTO) {

        List<Banner> banners = iBannerService.findAll();

        List<ProductResDTO> discounts = iProductService.findProductsDiscount();

        int sizeSale = 2;
        Pageable pageableSale = PageRequest.of(0, sizeSale);
        List<ProductResDTO> bestsales = iProductService.findTopSale(pageableSale);
        List<String> categories = enumToString.convert(ECategory.values());

        int sizeProduct = 60;
        Integer currentPage = Integer.parseInt(productPageReqDTO.getCurrentPage()) - 1;
        Pageable pageableProduct = PageRequest.of(currentPage, sizeProduct, Sort.by("id").descending());

        Page<ProductResDTO> products = iProductService.getPage(productPageReqDTO, pageableProduct);
        if (products.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NO_CONTENT, "Can't find product page request");
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("banners", banners);
        result.put("discounts", discounts);
        result.put("bestsales", bestsales);
        result.put("categories", categories);
        result.put("products", products);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
