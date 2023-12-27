package com.phung.clothshop.service.account;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.service.IGeneralService;

public interface IAccountService extends IGeneralService<Account, Long>, UserDetailsService {

    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);

    Account getByUsername(String username);
}
