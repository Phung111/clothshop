package com.phung.clothshop.service.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.dto.product.ProductImageDTO;
import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.dto.product.ProductReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.EProductStatus;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.product.ProductImage;
import com.phung.clothshop.repository.ProductRepository;
import com.phung.clothshop.service.discount.IDiscountService;
import com.phung.clothshop.service.productImage.IProductImageService;
import com.phung.clothshop.service.productImage.ProductImageService;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;

@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IProductImageService iProductImageService;

    @Autowired
    private IDiscountService iDiscountService;

    @Value("${application.cloudinary.default-file-name}")
    private String cloudinaryDefaultFileName;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long t) {
        return productRepository.getById(t);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product e) {
        e.setDeleted(true);
        productRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public Page<ProductResDTO> getPage(ProductPageReqDTO productShowReqDTO, Pageable pageable) {
        return productRepository.getPage(productShowReqDTO, pageable).map(Product::toProductResDTO);
    }

    @Override
    public ProductResDTO saveProductAndImage(ProductReqDTO productReqDTO)
            throws NumberFormatException, ParseException {

        Product product = productReqDTO.toProduct();
        product.setSold(0L);
        product.setProductStatus(EProductStatus.AVAIABLE);

        product = productRepository.save(product);

        List<ProductImage> productImages = new ArrayList<>();
        MultipartFile[] multipartFiles = productReqDTO.getMultipartFiles();
        if (multipartFiles != null && multipartFiles.length > 0 && !multipartFiles[0].getOriginalFilename().isEmpty()) {

            productImages = iProductImageService.uploadAndSaveImage(product, multipartFiles);

        } else {
            productImages = iProductImageService.setDefaultAndSaveImage(product);
        }
        product.setImages(productImages);
        
        String percent = productReqDTO.getPercent();
        String dateStart = productReqDTO.getDateStart();
        String dateEnd = productReqDTO.getDateEnd();
        if(!percent.equals('0') && !dateStart.trim().isEmpty() && !dateEnd.trim().isEmpty()) {
            Discount discount = iDiscountService.save(productReqDTO.toDiscount());
            product.setDiscount(discount);
        }

        ProductResDTO productResDTO = product.toProductResDTO();

        return productResDTO;
    }

    @Override
    public ProductResDTO updateProductAndImage(Product productOld, ProductReqDTO productReqDTO)
            throws NumberFormatException, ParseException {

        Product product = productReqDTO.toProduct();
        product.setId(productOld.getId());
        product.setSold(productOld.getSold());
        product.setProductStatus(productOld.getProductStatus());

        product = productRepository.save(product);
        
        // thêm lấy ra list product image cũ để xoá hết
        List<ProductImage> productImagesOld = iProductImageService.findByProduct_Id(productOld.getId());
        for (ProductImage productImage : productImagesOld) {
            iProductImageService.deleteProductImage(productImage);
        }

        List<ProductImage> productImagesNew = new ArrayList<>();
        MultipartFile[] multipartFiles = productReqDTO.getMultipartFiles();
        if (multipartFiles != null && multipartFiles.length > 0 && !multipartFiles[0].getOriginalFilename().isEmpty()) {
            productImagesNew = iProductImageService.uploadAndSaveImage(product, multipartFiles);
        } else {
            productImagesNew = iProductImageService.setDefaultAndSaveImage(product);
        }
        product.setImages(productImagesNew);

        String percent = productReqDTO.getPercent();
        String dateStart = productReqDTO.getDateStart();
        String dateEnd = productReqDTO.getDateEnd();
        if(!percent.equals('0') && !dateStart.trim().isEmpty() && !dateEnd.trim().isEmpty()) {
            Discount discount = iDiscountService.save(productReqDTO.toDiscount());
            product.setDiscount(discount);
        }
        
        ProductResDTO productResDTO = product.toProductResDTO();

        return productResDTO;
    }

    @Override
    public List<Product> findByDiscountId(Long discountId) {
        return productRepository.findByDiscountId(discountId);
    }

    @Override
    public List<ProductResDTO> findProductsDiscount(Pageable pageable) {

        List<ProductResDTO> productResDTOs = new ArrayList<>();
        List<Product> products = productRepository.findProductsDiscount(pageable);
        for (Product product : products) {
            productResDTOs.add(product.toProductResDTO());
        }
        return productResDTOs;
    }

    @Override
    public List<ProductResDTO> findTopSale(Pageable pageable) {

        List<ProductResDTO> productResDTOs = new ArrayList<>();
        List<Product> products = productRepository.findTopSale(pageable);
        for (Product product : products) {
            productResDTOs.add(product.toProductResDTO());
        }
        return productResDTOs;

    }

}
