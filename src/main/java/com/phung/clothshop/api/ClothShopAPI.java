package com.phung.clothshop.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.model.dto.ProductShowReqDTO;

@RestController
@RequestMapping("/api/clothshop")
public class ClothShopAPI {

    @GetMapping("/page={page}")
    public ResponseEntity<?> getProducts(
            @RequestBody ProductShowReqDTO productShowReqDTO,
            @PathVariable Long page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return new ResponseEntity<>(null);

    }
}
