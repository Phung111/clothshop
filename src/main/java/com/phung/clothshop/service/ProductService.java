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
import com.phung.clothshop.model.dto.ProductShowReqDTO;
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
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteId(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public Page<ProductShowResDTO> findAll(ProductShowReqDTO productShowReqDTO, Pageable pageable) {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductResDTO createWithImage(ProductCreateReqDTO productCreateReqDTO, MultipartFile[] multipartFiles) {
        Product productCreate = productRepository.save(productCreateReqDTO.toProduct());

        List<ProductImageDTO> imageDTOs = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            ProductImageDTO productImageDTO = productImageService.uploadAndSaveImage(productCreate, multipartFile);
            imageDTOs.add(productImageDTO);
        }

        ProductResDTO productResDTO = productCreate.toProductResDTO(imageDTOs);

        return productResDTO;
    }

    @Override
    public ProductResDTO createNoImage(ProductCreateReqDTO productCreateReqDTO) {
        Product productCreate = productRepository.save(productCreateReqDTO.toProduct());

        List<ProductImageDTO> imageDTOs = new ArrayList<>();

        ProductImageDTO productImageDTO = productImageService.setDefaultAndSaveImage(productCreate);

        imageDTOs.add(productImageDTO);

        ProductResDTO productResDTO = productCreate.toProductResDTO(imageDTOs);

        return productResDTO;
    }

    @Override
    public ProductResDTO updateWithImage(ProductUpdateReqDTO productUpdateReqDTO, MultipartFile[] multipartFiles) {
        Product productUpdate = productRepository.save(productUpdateReqDTO.toProduct());

        List<ProductImageDTO> imageDTOs = new ArrayList<>();

        ProductResDTO productResDTO = productUpdate.toProductResDTO(imageDTOs);
        return productResDTO;
    }

    @Override
    public ProductResDTO updateNoImage(ProductUpdateReqDTO productUpdateReqDTO) {
        Product productUpdate = productRepository.save(productUpdateReqDTO.toProduct());

        List<ProductImage> images = productUpdate.getImages();
        List<ProductImageDTO> imageDTOs = new ArrayList<>();
        for (ProductImage productImage : images) {
            imageDTOs.add(productImage.toProductImageDTO(productUpdate));
        }

        ProductResDTO productResDTO = productUpdate.toProductResDTO(imageDTOs);
        return productResDTO;
    }

}
