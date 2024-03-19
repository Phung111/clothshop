package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.entity.order.Voucher;

import java.sql.Date;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String>,
        JpaSpecificationExecutor<Voucher> {

    @Query("SELECT v FROM Voucher v WHERE CURRENT_DATE < v.dateEnd")
    List<Voucher> findValidVouchers();

}
