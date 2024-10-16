package org.hyeong.api1014.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.common.dto.PageRequestDTO;
import org.hyeong.api1014.common.dto.PageResponseDTO;
import org.hyeong.api1014.product.dto.ProductListDTO;
import org.hyeong.api1014.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize(("permitAll()"))
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(
            @Validated PageRequestDTO requestDTO
    ) {

        return ResponseEntity.ok(productService.list(requestDTO));
    }
}
