package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.entity.order.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>,
                JpaSpecificationExecutor<Bill> {

}
