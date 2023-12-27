package com.phung.clothshop.model.account;

import javax.persistence.*;

import com.phung.clothshop.model.BaseEntity;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.account.AccountResDTO;

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

    public AccountResDTO toAccountResDTO() {
        return new AccountResDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setERole(eRole.toString());
    }

}
