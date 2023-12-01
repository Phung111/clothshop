package com.phung.clothshop.model.product;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.phung.clothshop.model.dto.ProductImageDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_image")
@Where(clause = "deleted = false")
public class ProductImage {

    // @Id
    // @GeneratedValue(generator = "uuid")
    // @GenericGenerator(name = "uuid", strategy = "uuid2")
    // private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;

    @Column
    private Boolean deleted;

    public ProductImageDTO toProductImageDTO() {
        return new ProductImageDTO()
                .setId(id)
                .setProductId(product.getId())
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setFileType(fileType)
                .setCloudId(cloudId)
                .setDeleted(deleted);
    }
}
