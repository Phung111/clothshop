package com.phung.clothshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.phung.clothshop.domain.entity.customer.Address;

public interface AddressRepository extends JpaRepository<Address, Long>,
                JpaSpecificationExecutor<Address> {
        List<Address> findByCustomerId(Long customerId);

        Optional<Address> findByCustomerIdAndIsDefaultTrue(Long customerId);

}
