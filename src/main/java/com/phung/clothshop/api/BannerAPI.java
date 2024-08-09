package com.phung.clothshop.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phung.clothshop.domain.entity.banner.Banner;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.banner.IBannerService;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.ValidateMultipartFiles;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/banner")
public class BannerAPI {

    @Autowired
    private IBannerService iBannerService;

    @Autowired
    private ValidateMultipartFiles validatefiles;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("")
    public ResponseEntity<?> getAll() {

        List<Banner> banners = iBannerService.findAll();

        return new ResponseEntity<>(banners, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> create(MultipartFile[] files, BindingResult bindingResult) {

        validatefiles.validatefiles(files,bindingResult);
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        List<Banner> banners = iBannerService.uploadBanners(files);

        return new ResponseEntity<>(banners, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bannerID}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long bannerID) {

        Optional<Banner> bannerOptional = iBannerService.findById(bannerID);
        if (!bannerOptional.isPresent()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "Banner not found with ID: " + bannerID);
        }

        iBannerService.delete(bannerOptional.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletes")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deletes(List<Long> bannerIDs) {

        for (Long bannerID : bannerIDs) {
            Optional<Banner> bannerOptional = iBannerService.findById(bannerID);
            if (!bannerOptional.isPresent()) {
                throw new CustomErrorException(HttpStatus.NOT_FOUND, "Banner not found with ID: " + bannerID);
            }

            iBannerService.delete(bannerOptional.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
