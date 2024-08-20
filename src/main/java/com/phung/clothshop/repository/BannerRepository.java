package com.phung.clothshop.repository;

import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.dto.voucher.VoucherPageReqDTO;
import com.phung.clothshop.domain.entity.banner.Banner;
import com.phung.clothshop.domain.entity.order.Voucher;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>,
        JpaSpecificationExecutor<Banner> {

        default Page<Banner> getPage(Pageable pageable) {
                return findAll((root, query, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
                        query.orderBy(criteriaBuilder.desc(root.get("id")));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
