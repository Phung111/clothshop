package com.phung.clothshop.service.customer;

import java.text.ParseException;
import java.util.Optional;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.customer.Customer;
import com.phung.clothshop.model.dto.account.AccountRegisterReqDTO;
import com.phung.clothshop.service.IGeneralService;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    Optional<Customer> findByAccount(Account account);
}
