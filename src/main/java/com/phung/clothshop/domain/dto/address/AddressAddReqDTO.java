package com.phung.clothshop.domain.dto.address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.ship.EPronvince;
import com.phung.clothshop.utils.customAnnotation.EnumValidCheck;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressAddReqDTO {

    @NotBlank(message = "nameCustomer can not blank")
    private String nameCustomer;

    @NotBlank(message = "phone can not blank")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Phone is invalid!")
    private String phone;

    @NotBlank(message = "ePronvince can not blank")
    @EnumValidCheck(enumClass = EPronvince.class, message = "Invalid ePronvince value")
    private String ePronvince;

    @NotBlank(message = "address can not blank")
    private String address;

    private Boolean isDefault = false;

    public Address toAddress(Customer customer) {

        return new Address()
                .setNameCustomer(nameCustomer)
                .setPhone(phone)
                .setEPronvince(EPronvince.fromName(ePronvince))
                .setAddress(address)
                .setCustomer(customer)
                .setIsDefault(isDefault);
    }

}
