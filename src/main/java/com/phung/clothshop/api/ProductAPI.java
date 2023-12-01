package com.phung.clothshop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.product.ProductImage;
import com.phung.clothshop.ValidateMultipartFiles;
import com.phung.clothshop.model.dto.ProductCreateReqDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.dto.ProductUpdateReqDTO;
import com.phung.clothshop.service.IProductDetailService;
import com.phung.clothshop.service.IProductImageService;
import com.phung.clothshop.service.IProductService;
import com.phung.clothshop.utils.AppUtils;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IProductImageService iProductImageService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateMultipartFiles validateMultipartFiles;

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

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> producOptional = iProductService.findById(productId);
        if (!producOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }

        Product product = producOptional.get();

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createProduct(
            @ModelAttribute @Validated ProductCreateReqDTO productCreateReqDTO,
            BindingResult bindingResult) {

        // Kiểm tra lỗi ảnh và bắn tất cả lỗi
        validateMultipartFiles.validateMultipartFiles(productCreateReqDTO.getMultipartFiles(), bindingResult);

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        // Lưu sản phẩm
        Product productSave = iProductService.save(productCreateReqDTO.toProduct());

        // Lưu ảnh
        MultipartFile[] multipartFiles = productCreateReqDTO.getMultipartFiles();
        if (multipartFiles != null && multipartFiles.length > 0 && !multipartFiles[0].getOriginalFilename().isEmpty()) {
            for (MultipartFile multipartFile : multipartFiles) {
                iProductImageService.uploadAndSaveImage(productSave, multipartFile);
            }
        } else {
            iProductImageService.setDefaultAndSaveImage(productSave);
        }

        // Trả về sản phẩm
        Optional<Product> productOptional = iProductService.findById(productSave.getId());
        Product product = productOptional.get();
        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {

        Optional<Product> producOptional = iProductService.findById(productId);
        if (!producOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }

        Product product = producOptional.get();

        if (product.getDeleted() == true) {
            return ResponseEntity.status(HttpStatus.GONE).body("Product has already deleted with ID: " + productId);
        }
        iProductService.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product successfully with ID: " + productId);
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@Validated ProductUpdateReqDTO productUpdateReqDTO,
            BindingResult bindingResult,
            @PathVariable Long productId) {

        // Ánh xạ các trường DTO vào entity trừ null
        Optional<Product> producOptional = iProductService.findById(productId);
        Product productUpdate = producOptional.get();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(productUpdateReqDTO, productUpdate);

        // Kiểm tra lỗi ảnh và bắn tất cả lỗi
        validateMultipartFiles.validateMultipartFiles(productUpdateReqDTO.getMultipartFiles(),
                bindingResult);
        if (!producOptional.isPresent()) {
            bindingResult.rejectValue("productId", "notFound", "Product not found with ID: " + productId);
        }

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        // Lưu sản phẩm
        Product productUpdateNew = iProductService.save(productUpdate);

        // Xoá Ảnh
        List<Long> idImageDeletes = productUpdateReqDTO.getIdImageDeletes();
        if (idImageDeletes.size() > 0) {
            List<ProductImage> productImages = productUpdateNew.getImages();
            iProductImageService.deleteSelectImages(idImageDeletes, productImages);
        }

        ProductResDTO productResDTO = productUpdateNew.toProductResDTO();

        // Lưu ảnh
        MultipartFile[] multipartFiles = productUpdateReqDTO.getMultipartFiles();
        if (multipartFiles != null && multipartFiles.length > 0 &&
                !multipartFiles[0].getOriginalFilename().isEmpty()) {
            Optional<Product> productOptional = iProductService.findById(productUpdateNew.getId());
            Product product = productOptional.get();
            productResDTO = product.toProductResDTO();
        }

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }
}
