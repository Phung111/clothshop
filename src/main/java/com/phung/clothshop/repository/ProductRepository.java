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

import com.phung.clothshop.model.dto.product.ProductPageReqDTO;
import com.phung.clothshop.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    default Page<Product> getPage(ProductPageReqDTO productPageReqDTO, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            String keySearch = productPageReqDTO.getKeySearch();

            Boolean latest = productPageReqDTO.getLatest();
            Boolean topsales = productPageReqDTO.getTopsales();
            Boolean priceAsc = productPageReqDTO.getPriceAsc();

            String eCategory = productPageReqDTO.getECategory();
            String eTopLength = productPageReqDTO.getETopLength();
            String eCountry = productPageReqDTO.getECountry();
            String eSeason = productPageReqDTO.getESeason();
            String eStyle = productPageReqDTO.getEStyle();
            String eShipsFrom = productPageReqDTO.getEShipsFrom();

            String priceFrom = productPageReqDTO.getPriceFrom();
            String priceTo = productPageReqDTO.getPriceTo();

            if (keySearch != null) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + keySearch + "%");
                Predicate predicateDecription = criteriaBuilder.like(root.get("description"), "%" + keySearch + "%");
                Predicate predicateKeySearch = criteriaBuilder.or(predicateName, predicateDecription);
                predicates.add(predicateKeySearch);
            }

            if (latest != null && latest) {
                orders.add((Order) criteriaBuilder.desc(root.get("id")));
            }

            if (topsales != null && topsales) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sold")));
            }

            if (priceAsc != null && !priceAsc) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
            }

            if (eCategory != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("eCategory"), eCategory);
                predicates.add(predicate);
            }

            if (eTopLength != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("productDetail").get("eTopLength"), eTopLength);
                predicates.add(predicate);
            }

            if (eCountry != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("productDetail").get("eCountry"), eCountry);
                predicates.add(predicate);
            }

            if (eSeason != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("productDetail").get("eTopLength"), eSeason);
                predicates.add(predicate);
            }

            if (eStyle != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("productDetail").get("eTopLength"), eStyle);
                predicates.add(predicate);
            }

            if (eShipsFrom != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("productDetail").get("eTopLength"), eShipsFrom);
                predicates.add(predicate);
            }

            if (priceFrom != null && priceTo != null) {
                Predicate pricePredicate = criteriaBuilder.between(root.get("price"), priceFrom, priceTo);
                predicates.add(pricePredicate);
            } else if (priceFrom != null) {
                Predicate pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom);
                predicates.add(pricePredicate);
            } else if (priceTo != null) {
                Predicate pricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo);
                predicates.add(pricePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

}
