package com.phung.clothshop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.model.dto.product.ProductPageReqDTO;
import com.phung.clothshop.model.dto.product.ProductResDTO;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.repository.ProductRepository;
import com.phung.clothshop.service.productImage.ProductImageService;

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
    public Page<ProductResDTO> getPage(ProductPageReqDTO productShowReqDTO, Pageable pageable) {
        return productRepository.getPage(productShowReqDTO, pageable).map(Product::toProductResDTO);
    }

}
