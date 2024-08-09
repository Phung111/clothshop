package com.phung.clothshop.domain.dto.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPageReqDTO {

    private Integer stt;

    private String productSize;

    private String currentPage;

    private String keySearch;

    private Boolean latest;
    private Boolean nameAsc;
    private Boolean priceAsc;
    private Boolean topsales;

    private List<String> eCategories;
    private List<String> eTopLengths;
    private List<String> eCountries;
    private List<String> eSeasons;
    private List<String> eStyles;
    private List<String> eShipsFroms;

    private String priceFrom;
    private String priceTo;

}
