package com.phung.clothshop.model;

import java.util.Date;

import javax.persistence.*;

import com.phung.clothshop.model.product.EStatus;
import com.phung.clothshop.model.product.Product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column
    private Long quantity;

    @Column
    private Long total;

    @Column
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_status")
    private EStatus eStatus;

}
