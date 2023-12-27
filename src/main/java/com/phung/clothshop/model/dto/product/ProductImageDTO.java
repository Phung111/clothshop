package com.phung.clothshop.model.dto.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageDTO {

    private Long id;
    private Long productId;
    private String fileName;
    private String fileFolder;
    private String fileUrl;
    private String fileType;
    private String cloudId;
    private Boolean deleted;

}
