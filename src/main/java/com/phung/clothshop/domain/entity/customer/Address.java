package com.phung.clothshop.domain.entity.customer;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.phung.clothshop.domain.BaseEntity;
import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.entity.ship.EPronvince;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
@Where(clause = "deleted = false")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nameCustomer;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private EPronvince ePronvince;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column
    private Boolean isDefault;

    public AddressResDTO toAddressResDTO() {
        return new AddressResDTO()
                .setId(id)
                .setNameCustomer(nameCustomer)
                .setPhone(phone)
                .setEPronvince(ePronvince.getName())
                .setAddress(address)
                .setIsDefault(isDefault);
    }
}
