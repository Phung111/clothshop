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
    private ETopLength topLength;

    @Enumerated(EnumType.STRING)
    @Column
    private ECountry country;

    @Enumerated(EnumType.STRING)
    @Column
    private ESeason season;

    @Enumerated(EnumType.STRING)
    @Column
    private EStyle style;

    @Enumerated(EnumType.STRING)
    @Column
    private EShipsFrom shipsFrom;

}
