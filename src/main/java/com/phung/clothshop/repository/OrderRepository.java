package com.phung.clothshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.entity.order.Order;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
                JpaSpecificationExecutor<Order> {

    default Page<Order> getOrder(Pageable pageable) {
        return findAll(pageable);
    }

    default Page<Order> getOrderByCustomer(Pageable pageable, Long customerID) {
        return findAll((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.greaterThan(root.get("dateEnd"), criteriaBuilder.currentDate());
            return criteriaBuilder.and(predicate);
        }, pageable);
    }
}
