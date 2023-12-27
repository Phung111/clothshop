package com.phung.clothshop.model.customer;

import com.phung.clothshop.model.BaseEntity;
import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.order.Bill;
import com.phung.clothshop.model.order.Cart;
import com.phung.clothshop.model.order.Order;
import com.phung.clothshop.model.productDetail.ProductDetail;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long phone;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column
    private EGender eGender;

    @Column
    private Date dob;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;
}
