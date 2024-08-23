package com.phung.clothshop.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.dto.product.ProductReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.product.IProductService;
import com.phung.clothshop.service.productImage.IProductImageService;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.ValidateMultipartFiles;

@RestController
@RequestMapping("/api/product")
public class ProductAPI {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IProductImageService iProductImageService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateMultipartFiles validatefiles;

    @PostMapping("/get-product-page")
    public ResponseEntity<?> getPage(ProductPageReqDTO productPageReqDTO) {

        String sizeStr = productPageReqDTO.getProductSize();
        Integer sizeProduct;
        if( sizeStr !=null && !sizeStr.trim().isEmpty()) {
            sizeProduct = Integer.parseInt(productPageReqDTO.getProductSize()) ;
        } else {
            sizeProduct = 40;
        }

        Integer currentPage = Integer.parseInt(productPageReqDTO.getCurrentPage()) - 1;
        Pageable pageableProduct = PageRequest.of(currentPage, sizeProduct);

        Page<ProductResDTO> productResDTOs = iProductService.getPage(productPageReqDTO, pageableProduct);
        if (productResDTOs.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NO_CONTENT, "Can't find product page request");
        }
        return new ResponseEntity<>(productResDTOs, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProduct() {
        List<Product> products = iProductService.findAll();
        List<ProductResDTO> productResDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductResDTO productResDTO = product.toProductResDTO();
            productResDTOs.add(productResDTO);
        }
        return new ResponseEntity<>(productResDTOs, HttpStatus.OK);
    }

    @GetMapping("/{productID}")
    public ResponseEntity<?> getProductById(@PathVariable Long productID) {
        Optional<Product> productOptional = iProductService.findById(productID);
        if (!productOptional.isPresent()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productID);
        }

        Product product = productOptional.get();

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.OK);
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createProduct(
             @Validated ProductReqDTO productReqDTO,
            BindingResult bindingResult) throws NumberFormatException, ParseException {

        productReqDTO.validate(productReqDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        ProductResDTO productResDTO = iProductService.saveProductAndImage(productReqDTO);

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }

    
    @PostMapping("/update/{productID}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> updateProduct(@Validated ProductReqDTO productReqDTO,
                BindingResult bindingResult,
                @PathVariable Long productID
            ) throws NumberFormatException, ParseException{

        Optional<Product> producOptional = iProductService.findById(productID);
        if (!producOptional.isPresent()) {
            bindingResult.rejectValue("productID", "notFound", "Product not found with ID: " + productID);
        }

        productReqDTO.validate(productReqDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Product productOld = producOptional.get();

        ProductResDTO productResDTO = iProductService.updateProductAndImage(productOld, productReqDTO);

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productID}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productID) {

        Optional<Product> productOptional = iProductService.findById(productID);
        if (!productOptional.isPresent()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productID);
        }

        Product product = productOptional.get();

        if (product.getDeleted() == true) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Product has already deleted with ID: " + productID);
        }
        iProductService.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product successfully with ID: " + productID);
    }

    // thêm 1 hàm duyệt qua tất cả sản phẩm để thay đổi giá sản phầm có Discount và thời hạn vào dtb ở đây
}
