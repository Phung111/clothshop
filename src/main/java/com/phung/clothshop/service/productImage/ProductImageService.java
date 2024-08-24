package com.phung.clothshop.service.productImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.dto.product.ProductImageDTO;

import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.product.ProductImage;
import com.phung.clothshop.repository.ProductImageRepository;
import com.phung.clothshop.utils.CloudinaryUploader;

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
        e.setDeleted(true);
        productImageRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public List<ProductImage> uploadAndSaveImage(Product product, MultipartFile[] multipartFiles) {

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ProductImage productImage = new ProductImage();
            productImage.setDeleted(false);

            Map<?, ?> result = cloudinaryUploader.uploadToCloudinary(multipartFile, "product");
            productImage.setProduct(product);
            productImage.setFileFolder(cloudinaryDefaultFolder + "/product");
            productImage.setFileUrl(result.get("url").toString());
            productImage.setFileType(result.get("format").toString());
            productImage.setCloudId(result.get("public_id").toString());

            String fullPublicId = result.get("public_id").toString();
            String[] parts = fullPublicId.split("/");
            String fileNameString = parts[parts.length - 1];

            productImage.setFileName(fileNameString);

            productImage = productImageRepository.save(productImage);
            productImages.add(productImage);
        }

        return productImages;
    }

    @Override
    public List<ProductImage> setDefaultAndSaveImage(Product product) {

        String cloudId = cloudinaryDefaultFolder + "/" + cloudinaryDefaultFileName;
        String fileUrl = cloudinaryServerName + "/" + cloudinaryName + "/image/upload/" + cloudId;

        ProductImage productImage = new ProductImage();
        productImage.setDeleted(false);
        productImage.setProduct(product);
        productImage.setFileName(cloudinaryDefaultFileName);
        productImage.setFileFolder(cloudinaryDefaultFolder);
        productImage.setFileUrl(fileUrl);
        productImage.setFileType("png");
        productImage.setCloudId(cloudId);

        productImage = productImageRepository.save(productImage);

        List<ProductImage> productImages = new ArrayList<>();
        productImages.add(productImage);

        return productImages;
    }

    @Override
    public void deleteSelectImages(List<Long> idImageDeletes, List<ProductImage> productImages) {

        for (ProductImage oldImage : productImages) {
            if (idImageDeletes.size() > 0 && idImageDeletes.contains(oldImage.getId())) {
                productImageRepository.delete(oldImage);
            }
        }
    }

    @Override
    public List<ProductImage> findByProduct_Id(Long productId) {
        return productImageRepository.findByProduct_Id(productId);
    }

    @Override
    public void deleteProductImage(ProductImage productImage) {
        productImage.setDeleted(true);
        productImageRepository.save(productImage);
    }

    @Override
    public void deleteCloudinaryImages (List<ProductImage> productImages) {
        List<String> cloudIds = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            cloudIds.add(productImage.getCloudId());
        }
        cloudinaryUploader.deleteMultipleFromCloudinary(cloudIds);
    }

}
