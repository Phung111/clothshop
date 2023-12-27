package com.phung.clothshop.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPageReqDTO {

    private String currentPage;

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

    private String priceFrom;
    private String priceTo;

}
