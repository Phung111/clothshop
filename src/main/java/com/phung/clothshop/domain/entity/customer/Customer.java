package com.phung.clothshop.domain.entity.customer;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.account.AccountResDTO;
import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.cusomter.CustomerOrderResDTO;
import com.phung.clothshop.domain.dto.cusomter.CustomerResDTO;
import com.phung.clothshop.domain.entity.account.Account;
import com.phung.clothshop.domain.entity.order.Cart;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "customer", targetEntity = Address.class, fetch = FetchType.LAZY)
    private List<Address> addresses;

    public List<AddressResDTO> toAddressResDTO() {
        List<AddressResDTO> addressResDTOs = new ArrayList<>();
        for (Address address : addresses) {
            addressResDTOs.add(address.toAddressResDTO());
        }
        return addressResDTOs;
    }

    public CustomerResDTO customerResDTO() {
        return new CustomerResDTO()
                .setCustomerId(id)
                .setEmail(account.getUsername())
                .setRole(account.getERole().toString())
                .setName(name)
                .setGender(eGender.toString())
                .setDob(dob.toString());
    }

    public CustomerOrderResDTO toCustomerOrderResDTO() {

        List<AddressResDTO> addressResDTOs = toAddressResDTO();

        return new CustomerOrderResDTO()
                .setId(id)
                .setName(name)
                .setRole(account.getERole().toString())
                ;
    }

    

}
