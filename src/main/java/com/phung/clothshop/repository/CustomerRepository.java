package com.phung.clothshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.model.customer.Customer;
import java.util.List;
import com.phung.clothshop.model.account.Account;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>,
                JpaSpecificationExecutor<Customer> {

        Optional<Customer> findByAccount(Account account);

}
