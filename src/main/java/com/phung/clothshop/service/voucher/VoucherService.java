package com.phung.clothshop.service.voucher;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.dto.voucher.VoucherPageReqDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.domain.entity.product.Product;
import com.phung.clothshop.repository.VoucherRepository;

@Service
@Transactional
public class VoucherService implements IVoucherSevice {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getById(String t) {
        return voucherRepository.getById(t);
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Voucher save(Voucher e) {
        return voucherRepository.save(e);
    }

    @Override
    public void delete(Voucher e) {
        e.setDeleted(true);
        voucherRepository.save(e);
    }

    @Override
    public void deleteId(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public List<Voucher> findValidVouchers() {
        return voucherRepository.findValidVouchers();
    }

    @Override
    public Page<VoucherResDTO> getPage(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable) {
        return voucherRepository.getPage(voucherPageReqDTO, pageable).map(Voucher::toVoucherResDTO);
    }

    @Override
    public Page<VoucherResDTO> getPageWhenValid(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable) {
        return voucherRepository.getPageWhenValid(voucherPageReqDTO, pageable).map(Voucher::toVoucherResDTO);
    }
}
