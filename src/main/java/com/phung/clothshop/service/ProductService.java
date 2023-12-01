package com.phung.clothshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.CloudinaryUploader;
import com.phung.clothshop.model.dto.ProductCreateReqDTO;
import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.dto.ProductPaginationReqDTO;
import com.phung.clothshop.model.dto.ProductShowResDTO;
import com.phung.clothshop.model.dto.ProductUpdateReqDTO;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.product.ProductImage;
import com.phung.clothshop.repository.ProductRepository;

@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageService productImageService;

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
    public Page<ProductResDTO> getAllPagination(ProductPaginationReqDTO productShowReqDTO, Pageable pageable) {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductResDTO createWithImage(ProductCreateReqDTO productCreateReqDTO, MultipartFile[] multipartFiles) {
        Product productCreate = productRepository.save(productCreateReqDTO.toProduct());

        for (MultipartFile multipartFile : multipartFiles) {
            productImageService.uploadAndSaveImage(productCreate, multipartFile);
        }

        ProductResDTO productResDTO = productCreate.toProductResDTO();

        return productResDTO;
    }

    @Override
    public ProductResDTO createNoImage(ProductCreateReqDTO productCreateReqDTO) {
        Product productCreate = productRepository.save(productCreateReqDTO.toProduct());

        productImageService.setDefaultAndSaveImage(productCreate);

        ProductResDTO productResDTO = productCreate.toProductResDTO();

        return productResDTO;
    }

    @Override
    public ProductResDTO updateWithImage(List<Long> idImageDeletes, Product productUpdate,
            MultipartFile[] multipartFiles) {

        productRepository.save(productUpdate);

        productImageService.deleteSelectImages(idImageDeletes, productUpdate.getImages());

        for (MultipartFile multipartFile : multipartFiles) {
            productImageService.uploadAndSaveImage(productUpdate, multipartFile);
        }

        Optional<Product> producOptional = productRepository.findById(productUpdate.getId());
        Product productUpdateRes = producOptional.get();
        ProductResDTO productResDTO = productUpdateRes.toProductResDTO();

        return productResDTO;
    }

    @Override
    public ProductResDTO updateNoImage(List<Long> idImageDeletes, Product productUpdate) {

        Product productUpdateRes = productRepository.save(productUpdate);

        productImageService.deleteSelectImages(idImageDeletes, productUpdate.getImages());

        ProductResDTO productResDTO = productUpdateRes.toProductResDTO();

        return productResDTO;
    }

}
