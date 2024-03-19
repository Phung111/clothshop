package com.phung.clothshop.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.entity.order.Ship;
import com.phung.clothshop.domain.entity.product.Product;

public interface ShipRepository extends JpaRepository<Ship, Long>,
        JpaSpecificationExecutor<Ship> {

}
