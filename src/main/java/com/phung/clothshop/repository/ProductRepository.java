package com.phung.clothshop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.Order;

import com.phung.clothshop.domain.dto.product.ProductPageReqDTO;
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

    @Query("SELECT p FROM Product p WHERE p.discount IS NOT NULL AND p.discount.dateEnd > CURRENT_DATE ORDER BY p.id DESC")
    List<Product> findProductsDiscount(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.sold DESC")
    List<Product> findTopSale(Pageable pageable);

    default Page<Product> getPage(ProductPageReqDTO productPageReqDTO, Pageable pageable) {
        return findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            String keySearch = productPageReqDTO.getKeySearch();

            Boolean latest = productPageReqDTO.getLatest();
            Boolean nameAsc = productPageReqDTO.getNameAsc();
            Boolean priceAsc = productPageReqDTO.getPriceAsc();
            Boolean topsales = productPageReqDTO.getTopsales();

            String priceFromStr = productPageReqDTO.getPriceFrom();
            String priceToStr = productPageReqDTO.getPriceTo();

            List<String> eCategories = productPageReqDTO.getECategories();
            List<String> eTopLengths = productPageReqDTO.getETopLengths();
            List<String> eCountries = productPageReqDTO.getECountries();
            List<String> eSeasons = productPageReqDTO.getESeasons();
            List<String> eStyles = productPageReqDTO.getEStyles();
            List<String> eShipsFroms = productPageReqDTO.getEShipsFroms();

            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

            if (keySearch != null) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + keySearch + "%");
                Predicate predicateDecription = criteriaBuilder.like(root.get("decription"), "%" + keySearch + "%");
                Predicate predicateKeySearch = criteriaBuilder.or(predicateName, predicateDecription);
                predicates.add(predicateKeySearch);
            }


            if (latest != null && latest) {
                orders.add(criteriaBuilder.desc(root.get("id")));
                // criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
            } 
            if (latest != null && !latest) {
                orders.add(criteriaBuilder.asc(root.get("id")));
                // criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
            }
            

            if (nameAsc != null && nameAsc) {
                orders.add(criteriaBuilder.asc(root.get("name")));
                // criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
            }
            if (nameAsc != null && !nameAsc) {
                orders.add(criteriaBuilder.desc(root.get("name")));
                // criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
            }

            if (priceAsc != null && priceAsc) {
                orders.add(criteriaBuilder.asc(root.get("price")));
                // criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
            }
            if (priceAsc != null && !priceAsc) {
                orders.add(criteriaBuilder.desc(root.get("price")));
                // criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
            }

            if (topsales != null && topsales) {
                orders.add(criteriaBuilder.asc(root.get("sold")));
                // criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sold")));
            }
            if (topsales != null && !topsales) {
                orders.add(criteriaBuilder.desc(root.get("sold")));
                // criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sold")));
            }

            if (eCategories != null  && !eCategories.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eCategories) {
                    ECategory enumItem = ECategory.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("category"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eTopLengths != null && !eTopLengths.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eTopLengths) {
                    ETopLength enumItem = ETopLength.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("topLength"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eCountries != null && !eCountries.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eCountries) {
                    ECountry enumItem = ECountry.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("country"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eSeasons != null && !eSeasons.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eSeasons) {
                    ESeason enumItem = ESeason.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("season"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

            if (eStyles != null && !eStyles.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eStyles) {
                    EStyle enumItem = EStyle.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("style"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }

                        
            if (eShipsFroms != null && !eShipsFroms.isEmpty()) {
                List<Predicate> predicateList = new ArrayList<>();
                for (String item : eShipsFroms) {
                    EShipsFrom enumItem = EShipsFrom.valueOf(item);
                    Predicate predicateItem = criteriaBuilder.equal(root.get("productDetail").get("shipsFrom"), enumItem);
                    predicateList.add(predicateItem);
                }
                Predicate predicateAdd = criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
                predicates.add(predicateAdd);
            }
            
            if (priceFromStr != null && priceToStr != null && !priceFromStr.trim().isEmpty() && !priceToStr.trim().isEmpty()) {
                Long priceFrom = Long.parseLong(priceFromStr);
            Long priceTo = Long.parseLong(priceToStr);
                Predicate pricePredicate = criteriaBuilder.between(root.get("price"), priceFrom, priceTo);
                predicates.add(pricePredicate);
            } else if (priceFromStr != null && !priceFromStr.trim().isEmpty()) {
                Long priceFrom = Long.parseLong(priceFromStr);
                Predicate pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom);
                predicates.add(pricePredicate);
            } else if (priceToStr != null && !priceToStr.trim().isEmpty()) {
                Long priceTo = Long.parseLong(priceToStr);
                Predicate pricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo);
                predicates.add(pricePredicate);
            }   

            Predicate searchCondition = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            // // Thêm các điều kiện sắp xếp vào câu truy vấn
            criteriaQuery.where(searchCondition);
            criteriaQuery.orderBy(orders);

            // // Áp dụng điều kiện tìm kiếm và sắp xếp vào câu truy vấn
            return criteriaQuery.getRestriction();
            

            // return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    

    

}
