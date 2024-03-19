package com.phung.clothshop.domain.entity.productDetail;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private ETopLength eTopLength;

    @Enumerated(EnumType.STRING)
    @Column
    private ECountry eCountry;

    @Enumerated(EnumType.STRING)
    @Column
    private ESeason eSeason;

    @Enumerated(EnumType.STRING)
    @Column
    private EStyle eStyle;

    @Enumerated(EnumType.STRING)
    @Column
    private EShipsFrom eShipsFrom;

}
