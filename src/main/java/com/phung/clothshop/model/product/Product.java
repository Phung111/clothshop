package com.phung.clothshop.model.product;

import javax.persistence.*;
import lombok.*;

import com.phung.clothshop.model.dto.ProductImageDTO;
import com.phung.clothshop.model.dto.ProductResDTO;
import com.phung.clothshop.model.productDetail.ProductDetail;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Long quantity;

    @Column
    private Long sold;

    @Column
    private Boolean deleted;

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

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    public ProductResDTO toProductResDTO(List<ProductImageDTO> images) {
        return new ProductResDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .setSold(sold)
                .setDeleted(deleted)
                .setDecription(decription)
                .setECategory(eCategory)
                .setEColor(eColor)
                .setESize(eSize)
                .setEProductStatus(eProductStatus)
                .setProductDetail(productDetail)
                .setImages(images);

    }
}
