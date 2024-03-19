package com.phung.clothshop.service.address;

import java.util.List;
import java.util.Optional;

import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.service.IGeneralService;

public interface IAddressService extends IGeneralService<Address, Long> {
    List<Address> findByCustomerId(Long customerId);

    Optional<Address> findByCustomerIdAndIsDefaultTrue(Long customerId);

}
