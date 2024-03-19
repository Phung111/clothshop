package com.phung.clothshop.service.bill;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.entity.order.Bill;
import com.phung.clothshop.repository.BillRepository;
import com.phung.clothshop.repository.CartItemRepository;
import com.phung.clothshop.repository.CartRepository;
import com.phung.clothshop.repository.CustomerRepository;
import com.phung.clothshop.repository.OrderItemRepository;
import com.phung.clothshop.repository.OrderRepository;
import com.phung.clothshop.repository.ProductRepository;

@Service
@Transactional
public class BillService implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill getById(Long t) {
        return billRepository.getById(t);
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Bill save(Bill e) {
        return billRepository.save(e);
    }

    @Override
    public void delete(Bill e) {
        e.setDeleted(true);
        billRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

}
