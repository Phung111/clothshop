package com.phung.clothshop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.product.ProductImage;
import com.phung.clothshop.model.dto.ProductCreateReqDTO;
import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.dto.ProductUpdateReqDTO;
import com.phung.clothshop.service.IProductDetailService;
import com.phung.clothshop.service.IProductService;
import com.phung.clothshop.utils.AppUtils;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IProductDetailService iProductDetailService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProduct() {

        return null;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById() {

        return null;
    }

    @PostMapping("/create")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createProduct(
            @Validated ProductCreateReqDTO productCreateReqDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        ProductResDTO productResDTO = new ProductResDTO();

        MultipartFile[] multipartFiles = productCreateReqDTO.getMultipartFiles();

        if (multipartFiles != null && multipartFiles.length > 0 && !multipartFiles[0].getOriginalFilename().isEmpty()) {
            productResDTO = iProductService.createWithImage(productCreateReqDTO, multipartFiles);
        } else {
            productResDTO = iProductService.createNoImage(productCreateReqDTO);
        }

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct() {

        return null;
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@Validated ProductUpdateReqDTO productUpdateReqDTO,
            @PathVariable Long productId,
            BindingResult bindingResult) {

        Optional<Product> producOptional = iProductService.findById(productId);

        if (!producOptional.isPresent()) {
            bindingResult.rejectValue("productId", "notFound", "Product not found with ID: " + productId);
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Product product = producOptional.get();

        ProductResDTO productResDTO = new ProductResDTO();

        MultipartFile[] multipartFiles = productUpdateReqDTO.getMultipartFiles();
        for (MultipartFile multipartFile : multipartFiles) {

        }
        // 3 trường hợp

        // nếu có ảnh
        //// ảnh là default thì setDeleted(true)
        //// lưu sp
        //// tạo list ảnh mới
        //// lưu list ảnh mới
        //// gọi dtb lấy product về

        // nếu k ảnh
        /// lưu sp

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
