package com.phung.clothshop.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pagination {
    
    private Long page;
    private Long productPerPage;
    private String type;
    private String keySearch;
    
}
