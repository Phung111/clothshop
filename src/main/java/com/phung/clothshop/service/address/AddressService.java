package com.phung.clothshop.service.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.repository.AddressRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address getById(Long t) {
        return addressRepository.getById(t);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address save(Address e) {
        return addressRepository.save(e);
    }

    @Override
    public void delete(Address e) {
        e.setDeleted(true);
        addressRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public List<Address> findByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<Address> findByCustomerIdAndIsDefaultTrue(Long customerId) {
        return addressRepository.findByCustomerIdAndIsDefaultTrue(customerId);
    }

}
