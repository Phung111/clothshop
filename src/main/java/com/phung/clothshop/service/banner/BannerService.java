package com.phung.clothshop.service.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phung.clothshop.domain.entity.banner.Banner;
import com.phung.clothshop.repository.BannerRepository;
import com.phung.clothshop.utils.CloudinaryUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class BannerService implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CloudinaryUploader cloudinaryUploader;

    @Value("${application.cloudinary.server-name}")
    private String cloudinaryServerName;

    @Value("${application.cloudinary.cloud-name}")
    private String cloudinaryName;

    @Value("${application.cloudinary.default-folder}")
    private String cloudinaryDefaultFolder;

    @Value("${application.cloudinary.default-file-name}")
    private String cloudinaryDefaultFileName;

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner getById(Long t) {
        return bannerRepository.getById(t);
    }

    @Override
    public Optional<Banner> findById(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public Banner save(Banner e) {
        return bannerRepository.save(e);
    }

    @Override
    public void delete(Banner e) {
        e.setDeleted(true);
        bannerRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }

    @Override
    public List<Banner> uploadBanners(MultipartFile[] multipartFiles) {

        List<Banner> banners = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            Banner banner = new Banner();

            Map<?, ?> result = cloudinaryUploader.uploadToCloudinary(multipartFile, "banner");
            banner.setFileFolder(cloudinaryDefaultFolder + "/banner");
            banner.setFileUrl(result.get("url").toString());
            banner.setFileType(result.get("format").toString());
            banner.setCloudId(result.get("public_id").toString());

            String fullPublicId = result.get("public_id").toString();
            String[] parts = fullPublicId.split("/");
            String fileNameString = parts[parts.length - 1];

            banner.setFileName(fileNameString);

            banner = bannerRepository.save(banner);
            banners.add(banner);
        }
        return banners;
    }

}
