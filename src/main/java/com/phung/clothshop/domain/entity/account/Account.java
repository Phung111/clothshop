package com.phung.clothshop.domain.entity.account;

import javax.persistence.*;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.account.AccountResDTO;
import com.phung.clothshop.domain.entity.customer.Customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_role", nullable = false)
    private ERole eRole;

    

}
