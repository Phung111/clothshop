package com.phung.clothshop.service.voucher;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phung.clothshop.domain.dto.voucher.VoucherPageReqDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.service.IGeneralService;

public interface IVoucherSevice extends IGeneralService<Voucher, String> {

    List<Voucher> findValidVouchers();

    Page<VoucherResDTO> getPage(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable);

    Page<VoucherResDTO> getPageWhenValid(VoucherPageReqDTO voucherPageReqDTO, Pageable pageable);

}
