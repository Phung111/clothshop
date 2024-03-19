package com.phung.clothshop.repository;

import org.springframework.stereotype.Repository;

import com.phung.clothshop.domain.entity.banner.Banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>,
        JpaSpecificationExecutor<Banner> {

}
