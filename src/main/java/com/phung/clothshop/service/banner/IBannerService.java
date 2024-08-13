package com.phung.clothshop.service.banner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.entity.banner.Banner;
import com.phung.clothshop.service.IGeneralService;

public interface IBannerService extends IGeneralService<Banner, Long> {

    List<Banner> uploadBanners(MultipartFile[] multipartFiles);

    Page<Banner> getPage(Pageable pageable);

}
