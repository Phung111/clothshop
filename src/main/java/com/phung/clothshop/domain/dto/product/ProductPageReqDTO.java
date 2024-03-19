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

    private String currentPage = "1";

    private String keySearch;

    private Boolean latest;
    private Boolean topsales;
    private Boolean priceAsc;

    private String eCategory;
    private String eTopLength;
    private String eCountry;
    private String eSeason;
    private String eStyle;
    private String eShipsFrom;

    private List<String> eCategories;
    private List<String> eTopLengths;
    private List<String> eCountries;
    private List<String> eSeasons;
    private List<String> eStyles;
    private List<String> eShipsFroms;

    private String priceFrom;
    private String priceTo;

}
