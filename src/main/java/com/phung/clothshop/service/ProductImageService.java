package com.phung.clothshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.CloudinaryUploader;
import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.product.Product;
import com.phung.clothshop.model.product.ProductImage;
import com.phung.clothshop.repository.ProductImageRepository;

@Service
@Transactional
public class ProductImageService implements IProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CloudinaryUploader cloudinaryUploader;

    @Value("${application.cloudinary.server-name}")
    private String cloudinaryServerName;

    @Value("${application.cloudinary.cloud-name}")
    private String cloudinaryName;

    @Value("${application.cloudinary.default-folder}")
    private String cloudinaryDefaultFolder;

    @Value("${application.cloudinary.default-file-name}")
    private String cloudinaryDefaultFileName;

    @Override
    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public ProductImage getById(Long t) {
        return productImageRepository.getById(t);
    }

    @Override
    public Optional<ProductImage> findById(Long id) {
        return productImageRepository.findById(id);
    }

    @Override
    public ProductImage save(ProductImage e) {
        return productImageRepository.save(e);
    }

    @Override
    public void delete(ProductImage e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public ProductImageDTO uploadAndSaveImage(Product product, MultipartFile multipartFile) {
        ProductImage productImage = new ProductImage();
        productImage.setDeleted(false);

        Map<?, ?> result = cloudinaryUploader.uploadToCloudinary(multipartFile);
        productImage.setProduct(product);
        productImage.setFileFolder(cloudinaryDefaultFolder);
        productImage.setFileUrl(result.get("url").toString());
        productImage.setFileType(result.get("format").toString());
        productImage.setCloudId(result.get("public_id").toString());

        String fullPublicId = result.get("public_id").toString();
        String[] parts = fullPublicId.split("/");
        String fileNameString = parts[parts.length - 1];

        productImage.setFileName(fileNameString);

        productImage = productImageRepository.save(productImage);

        ProductImageDTO productImageDTO = productImage.toProductImageDTO(product);

        return productImageDTO;
    }

    @Override
    public ProductImageDTO setDefaultAndSaveImage(Product product) {
        String cloudId = cloudinaryDefaultFolder + "/" + cloudinaryDefaultFileName;
        String fileUrl = cloudinaryServerName + "/" + cloudinaryName + "/image/upload/" + cloudId;

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setFileName(cloudinaryDefaultFileName);
        productImage.setFileFolder(cloudinaryDefaultFolder);
        productImage.setFileUrl(fileUrl);
        productImage.setFileType("png");
        productImage.setCloudId(cloudId);

        productImage = productImageRepository.save(productImage);

        ProductImageDTO productImageDTO = productImage.toProductImageDTO(product);

        return productImageDTO;
    }

}
