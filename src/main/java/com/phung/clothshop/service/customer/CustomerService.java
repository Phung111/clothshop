package com.phung.clothshop.service.customer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.account.AccountRegisterReqDTO;
import com.phung.clothshop.model.order.Cart;
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

    // @Override
    // public Customer saveCustomerWithAccountAndCart(Customer customer, Account
    // account, Cart cart) {
    // try {
    // // Mã hóa mật khẩu trước khi lưu Account
    // account.setPassword(passwordEncoder.encode(account.getPassword()));

    // // Lưu Account
    // Account savedAccount = accountRepository.save(account);

    // // Liên kết Account và Customer
    // customer.setAccount(savedAccount);

    // // Lưu Cart
    // Cart savedCart = cartRepository.save(cart);

    // // Liên kết Customer và Cart
    // customer.setCart(savedCart);

    // // Lưu Customer
    // Customer savedCustomer = customerRepository.save(customer);

    // return savedCustomer;
    // } catch (Exception ex) {
    // // Xử lý ngoại lệ hoặc ném ra ngoại lệ để kích hoạt rollback
    // throw new RuntimeException("Error occurred during saving customer: " +
    // ex.getMessage());
    // }
    // }

}
