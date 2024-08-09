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
import com.phung.clothshop.domain.entity.customer.EGender;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.ECategory;
import com.phung.clothshop.domain.entity.product.EColor;
import com.phung.clothshop.domain.entity.product.ESize;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.productDetail.ECountry;
import com.phung.clothshop.domain.entity.productDetail.ESeason;
import com.phung.clothshop.domain.entity.productDetail.EShipsFrom;
import com.phung.clothshop.domain.entity.productDetail.EStyle;
import com.phung.clothshop.domain.entity.productDetail.ETopLength;
import com.phung.clothshop.domain.entity.ship.EPronvince;
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

    @GetMapping("get-home")
    public ResponseEntity<?> getHome(ProductPageReqDTO productPageReqDTO) {

        List<Banner> banners = iBannerService.findAll();

        int size = 18;
        Pageable pageable= PageRequest.of(0, size);

        List<ProductResDTO> discounts = iProductService.findProductsDiscount(pageable);

        
        List<ProductResDTO> bestsales = iProductService.findTopSale(pageable);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("banners", banners);
        result.put("discounts", discounts);
        result.put("bestsales", bestsales);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("get-collection")
    public ResponseEntity<?> getCollection() {
        List<String> colors = enumToString.convert(EColor.values());
        List<String> sizes = enumToString.convert(ESize.values());
        List<String> categories = enumToString.convert(ECategory.values());
        List<String> topLengths = enumToString.convert(ETopLength.values());
        List<String> countries = enumToString.convert(ECountry.values());
        List<String> seasons = enumToString.convert(ESeason.values());
        List<String> styles = enumToString.convert(EStyle.values());
        List<String> shipfroms = enumToString.convert(EShipsFrom.values());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("colors", colors);
        result.put("sizes", sizes);
        result.put("categories", categories);
        result.put("topLengths", topLengths);
        result.put("countries", countries);
        result.put("seasons", seasons);
        result.put("styles", styles);
        result.put("shipfroms", shipfroms);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("getPronvice")
    public ResponseEntity<?> getPronvice() {
        List<String> pronvices = enumToString.convertToName(EPronvince.values());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pronvices", pronvices);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("getGender")
    public ResponseEntity<?> getGender() {
        List<String> genders = enumToString.convertToName(EGender.values());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("genders", genders);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
