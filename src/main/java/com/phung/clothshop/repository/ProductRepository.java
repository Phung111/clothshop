package com.phung.clothshop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.dto.discount.DiscountResDTO;
import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.ECategory;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.domain.entity.productDetail.ECountry;
import com.phung.clothshop.domain.entity.productDetail.ESeason;
import com.phung.clothshop.domain.entity.productDetail.EShipsFrom;
import com.phung.clothshop.domain.entity.productDetail.EStyle;
import com.phung.clothshop.domain.entity.productDetail.ETopLength;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    List<Product> findByDiscountId(Long discountId);

    @Query("SELECT p FROM Product p WHERE p.discount IS NOT NULL AND p.discount.dateEnd > CURRENT_DATE")
    List<Product> findProductsDiscount();

    @Query("SELECT p FROM Product p ORDER BY p.sold DESC")
    List<Product> findTopSale(Pageable pageable);

    // default Page<Product> findProductsWithValidDiscount(Pageable pageable) {
    // return findAll((root, criteriaQuery, criteriaBuilder) -> {
    // criteriaQuery.distinct(true);

    // List<Predicate> predicates = new ArrayList<>();

    // Predicate discountNotNullPredicate =
    // criteriaBuilder.isNotNull(root.get("discount"));
    // Predicate dateStartPredicate = criteriaBuilder.lessThanOrEqualTo(
    // criteriaBuilder.currentDate(),
    // root.get("discount").get("dateStart"));
    // Predicate dateEndPredicate = criteriaBuilder.greaterThanOrEqualTo(
    // criteriaBuilder.currentDate(),
    // root.get("discount").get("dateEnd"));

    // Predicate validDiscountPredicate = criteriaBuilder.and(
    // discountNotNullPredicate,
    // dateStartPredicate,
    // dateEndPredicate);

    // predicates.add(validDiscountPredicate);

    // return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    // }, pageable);
    // }

    default Page<Product> getPage(ProductPageReqDTO productPageReqDTO, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            String keySearch = productPageReqDTO.getKeySearch();

            Boolean latest = productPageReqDTO.getLatest();
            Boolean topsales = productPageReqDTO.getTopsales();
            Boolean priceAsc = productPageReqDTO.getPriceAsc();

            String priceFrom = productPageReqDTO.getPriceFrom();
            String priceTo = productPageReqDTO.getPriceTo();

            List<String> eCategories = productPageReqDTO.getECategories();
            List<String> eTopLengths = productPageReqDTO.getETopLengths();
            List<String> eCountries = productPageReqDTO.getECountries();
            List<String> eSeasons = productPageReqDTO.getESeasons();
            List<String> eStyles = productPageReqDTO.getEStyles();
            List<String> eShipsFroms = productPageReqDTO.getEShipsFroms();

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

            if (eCategories != null  && !eCategories.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eCategories) {
                    ECategory enumItem = ECategory.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("eCategory"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eTopLengths != null && !eTopLengths.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eTopLengths) {
                    ETopLength enumItem = ETopLength.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("eToplength"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eCountries != null && !eCountries.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eCountries) {
                    ECountry enumItem = ECountry.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("eCountry"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eSeasons != null && !eSeasons.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eSeasons) {
                    ESeason enumItem = ESeason.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("eSeason"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eStyles != null && !eStyles.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eStyles) {
                    EStyle enumItem = EStyle.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("eStyle"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

                        
            if (eShipsFroms != null && !eShipsFroms.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eShipsFroms) {
                    EShipsFrom enumItem = EShipsFrom.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("eShipsFrom"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
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
