package com.phung.clothshop.service.discount;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.phung.clothshop.domain.dto.discount.DiscountCreateReqDTO;
import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.entity.product.Discount;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.repository.DiscountRepository;
import com.phung.clothshop.service.product.IProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiscountService implements IDiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private IProductService iProductService;

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount getById(Long t) {
        return discountRepository.getById(t);
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    public Discount save(Discount e) {
        return discountRepository.save(e);
    }

    @Override
    public void delete(Discount e) {
        e.setDeleted(true);
        discountRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public List<Discount> findExpiredDiscounts() {
        return discountRepository.findExpiredDiscounts();
    }

    @Override
    public List<ProductResDTO> createDiscount(DiscountCreateReqDTO discountCreateReqDTO) throws ParseException {

        Discount discount = discountCreateReqDTO.toDiscount();
        discount = discountRepository.save(discount);

        List<ProductResDTO> productResDTOs = new ArrayList<>();

        List<Long> productIDs = discountCreateReqDTO.getProductIDs();
        for (Long productID : productIDs) {
            Optional<Product> productOptional = iProductService.findById(productID);
            if (!productOptional.isPresent()) {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Product not found with ID: " + productID);
            }

            Product product = productOptional.get();
            product.setDiscount(discount);
            product = iProductService.save(product);

            productResDTOs.add(product.toProductResDTO());
        }

        return productResDTOs;
    }

}
