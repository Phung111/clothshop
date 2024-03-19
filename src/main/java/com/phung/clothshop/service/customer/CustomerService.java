package com.phung.clothshop.service.customer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.dto.account.AccountRegisterReqDTO;
import com.phung.clothshop.domain.entity.account.Account;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.domain.entity.order.Cart;
import com.phung.clothshop.repository.AccountRepository;
import com.phung.clothshop.repository.CartRepository;
import com.phung.clothshop.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Long t) {
        return customerRepository.getById(t);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer e) {
        return customerRepository.save(e);
    }

    @Override
    public void delete(Customer e) {
        e.setDeleted(true);
        customerRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public Optional<Customer> findByAccount(Account account) {
        return customerRepository.findByAccount(account);
    }

}
