package com.phung.clothshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductShowReqDTO {

    private Long productPerPage;
    private String type;
    private String keySearch;
    
}
