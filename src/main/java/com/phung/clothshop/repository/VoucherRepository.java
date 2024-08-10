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
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.greaterThan(root.get("dateEnd"), criteriaBuilder.currentDate()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    default Page<Voucher> getPageWhenValid(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.greaterThan(root.get("dateEnd"), criteriaBuilder.currentDate()));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateStart"), criteriaBuilder.currentDate()));
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
    

}
