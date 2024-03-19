package com.phung.clothshop.service.voucher;

import java.sql.Date;
import java.util.List;

import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.service.IGeneralService;

public interface IVoucherSevice extends IGeneralService<Voucher, String> {
    List<Voucher> findValidVouchers();
}
