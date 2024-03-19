package com.phung.clothshop.domain.entity.product;

import javax.persistence.*;
import lombok.*;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.product.ProductImageDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.productDetail.ProductDetail;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_id", referencedColumnName = "id", nullable = true)
    private Discount discount;

    @Column
    private Long priceTotal;

    @Column
    private Long quantity;

    @Column
    private Long sold;

    @Column
    private String decription;

    @Enumerated(EnumType.STRING)
    @Column
    private ECategory eCategory;

    @Enumerated(EnumType.STRING)
    @Column
    private EColor eColor;

    @Enumerated(EnumType.STRING)
    @Column
    private ESize eSize;

    @Enumerated(EnumType.STRING)
    @Column
    private EProductStatus eProductStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id", nullable = false)
    private ProductDetail productDetail;

    @OneToMany(mappedBy = "product", targetEntity = ProductImage.class, fetch = FetchType.EAGER)
    private List<ProductImage> images;

    public ProductResDTO toProductResDTO() {

        List<ProductImageDTO> productImageDTOs = new ArrayList<>();
        for (ProductImage productImage : images) {
            productImageDTOs.add(productImage.toProductImageDTO());
        }

        ProductResDTO productResDTO = new ProductResDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setDiscountResDTO(null)
                .setPriceTotal(price)
                .setQuantity(quantity)
                .setSold(sold)
                .setDecription(decription)
                .setECategory(eCategory)
                .setEColor(eColor)
                .setESize(eSize)
                .setEProductStatus(eProductStatus)
                .setProductDetail(productDetail)
                .setImages(productImageDTOs)
                .setDeleted(getDeleted())
                .setCreatedAt(getCreatedAt())
                .setUpdatedAt(getUpdatedAt());

        if (discount != null) {
            productResDTO.setDiscountResDTO(discount.toDiscountResDTO());
            if (discount.getPercent() != null) {
                Long priceTotal = price - price * discount.getPercent() / 100;
                productResDTO.setPriceTotal(priceTotal);
            }
        }

        return productResDTO;
    }

    public String toVariations() {
        String variations = eCategory.toString() + "," + eColor.toString() + "," + eSize.toString();
        return variations;
    }
}
