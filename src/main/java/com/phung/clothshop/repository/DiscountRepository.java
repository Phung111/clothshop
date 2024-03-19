package com.phung.clothshop.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.phung.clothshop.domain.entity.product.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>,
                JpaSpecificationExecutor<Discount> {

        @Query("SELECT d FROM Discount d WHERE d.dateEnd < CURRENT_DATE")
        List<Discount> findExpiredDiscounts();
}
