package com.phung.clothshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.dto.voucher.VoucherPageReqDTO;
import com.phung.clothshop.domain.entity.order.Voucher;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String>,
        JpaSpecificationExecutor<Voucher> {

    @Query("SELECT v FROM Voucher v WHERE CURRENT_DATE < v.dateEnd")
    List<Voucher> findValidVouchers();


    default Page<Voucher> getPage(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable) {
        return findAll((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.greaterThan(root.get("dateEnd"), criteriaBuilder.currentDate());
            return criteriaBuilder.and(predicate);
        }, pageable);
    }

    default Page<Voucher> getPageWhenValid(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable) {
        return findAll((root, query, cb) -> {
            Predicate dateEndPredicate = cb.greaterThan(root.get("dateEnd"), cb.currentDate());
            Predicate dateStartPredicate = cb.lessThanOrEqualTo(root.get("dateStart"), cb.currentDate());
            return cb.and(dateEndPredicate, dateStartPredicate);
        }, pageable);
    }
    

}
