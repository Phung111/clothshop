package com.phung.clothshop.domain.dto.banner;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BannerReqDTO {

    private MultipartFile[] files;
}
