package com.phung.clothshop.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.product.ProductResDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherCreateReqDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherPageReqDTO;
import com.phung.clothshop.domain.dto.voucher.VoucherResDTO;
import com.phung.clothshop.domain.dto.voucher.VouchersAndQuantityResDTO;
import com.phung.clothshop.domain.entity.order.Voucher;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.voucher.IVoucherSevice;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.DateFormat;

@RestController
@RequestMapping("/api/voucher")
public class VoucherAPI {

    @Autowired
    private IVoucherSevice iVoucherSevice;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/get-voucher-page")
    public ResponseEntity<?> getPage(VoucherPageReqDTO voucherPageReqDTO) throws ParseException {

        String sizeStr = voucherPageReqDTO.getSize();
        Integer size = (sizeStr != null && !sizeStr.trim().isEmpty()) ? Integer.parseInt(sizeStr) : 15;

        String currentPageStr = voucherPageReqDTO.getPage();
        Integer currentPage = (currentPageStr != null && !currentPageStr.trim().isEmpty()) ? Integer.parseInt(currentPageStr) - 1 : 0;

        Pageable pageable = PageRequest.of(currentPage, size);

        Page<VoucherResDTO> voucherResDTOs = iVoucherSevice.getPage(voucherPageReqDTO, pageable);
        if (voucherResDTOs.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NO_CONTENT, "Can't find voucher page request");
        }

        return new ResponseEntity<>(voucherResDTOs, HttpStatus.OK);
    }

    @PostMapping("/get-voucher-page-valid")
    public ResponseEntity<?> getPageWhenValid(VoucherPageReqDTO voucherPageReqDTO) throws ParseException {

        String sizeStr = voucherPageReqDTO.getSize();
        Integer size = (sizeStr != null && !sizeStr.trim().isEmpty()) ? Integer.parseInt(sizeStr) : 15;

        String currentPageStr = voucherPageReqDTO.getPage();
        Integer currentPage = (currentPageStr != null && !currentPageStr.trim().isEmpty()) ? Integer.parseInt(currentPageStr) - 1 : 0;

        // Integer currentPage = Integer.parseInt(voucherPageReqDTO.getCurrentPage()) - 1;
        Pageable pageable = PageRequest.of(currentPage, size);

        Page<VoucherResDTO> voucherResDTOs = iVoucherSevice.getPageWhenValid(voucherPageReqDTO, pageable);
        if (voucherResDTOs.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NO_CONTENT, "Can't find voucher page request");
        }

        return new ResponseEntity<>(voucherResDTOs, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getVoucher() throws ParseException {

        List<VoucherResDTO> voucherCreatResDTOs = new ArrayList<>();

        List<Voucher> vouchers = iVoucherSevice.findValidVouchers();
        for (Voucher voucher : vouchers) {
            voucherCreatResDTOs.add(voucher.toVoucherResDTO());
        }

        VouchersAndQuantityResDTO vouchersAndQuantityResDTO = new VouchersAndQuantityResDTO();
        vouchersAndQuantityResDTO.setVouchers(voucherCreatResDTOs);

        return new ResponseEntity<>(vouchersAndQuantityResDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createVoucher(@Validated VoucherCreateReqDTO voucherCreateReqDTO, BindingResult bindingResult)
            throws ParseException {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Date currentDate = new Date();
        Date startDate = DateFormat.parse(voucherCreateReqDTO.getDateStart());
        Date endDate = DateFormat.parse(voucherCreateReqDTO.getDateEnd());

        

        if (currentDate.after(startDate)) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "START DATE must be after CURRENT DATE");
        }
        if (currentDate.after(endDate)) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "END DATE must be after CURRENT DATE");
        }
        if (startDate.after(endDate)) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "END DATE must be after START DATE");
        }

        List<VoucherResDTO> voucherCreatResDTOs = new ArrayList<>();

        Long quantity = Long.parseLong(voucherCreateReqDTO.getQuantity());

        for (int i = 0; i < quantity; i++) {
            Voucher voucher = iVoucherSevice.save(voucherCreateReqDTO.toVoucher());
            voucherCreatResDTOs.add(voucher.toVoucherResDTO());
        }

        VouchersAndQuantityResDTO vouchersAndQuantityResDTO = new VouchersAndQuantityResDTO();
        vouchersAndQuantityResDTO.setVouchers(voucherCreatResDTOs);

        return new ResponseEntity<>(voucherCreatResDTOs, HttpStatus.OK);
    }

    
    @PostMapping("chose/{voucherID}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> chose(@PathVariable String voucherID) {

        Optional<Voucher> voucherOptional = iVoucherSevice.findById(voucherID);
        Voucher voucher = voucherOptional.get();
        voucher.checkDate();

        VoucherResDTO voucherResDTO = voucherOptional.get().toVoucherResDTO();

        return new ResponseEntity<>(voucherResDTO, HttpStatus.OK);
    }

}
