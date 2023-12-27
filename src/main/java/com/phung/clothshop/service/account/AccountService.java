package com.phung.clothshop.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.model.account.Account;
import com.phung.clothshop.model.account.AccountPrinciple;
import com.phung.clothshop.repository.AccountRepository;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(Long t) {
        return accountRepository.getById(t);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account e) {
        e.setPassword(passwordEncoder.encode(e.getPassword()));
        return accountRepository.save(e);
    }

    @Override
    public void delete(Account e) {
        e.setDeleted(true);
        accountRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {

    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accouOptional = accountRepository.findByUsername(username);

        if (!accouOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return AccountPrinciple.build(accouOptional.get());
    }

    @Override
    public Account getByUsername(String username) {
        return accountRepository.getByUsername(username);
    }

}
