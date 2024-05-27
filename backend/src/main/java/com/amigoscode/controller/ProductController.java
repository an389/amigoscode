package com.amigoscode.controller;

import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.model.api.ProductRegistrationRequest;
import com.amigoscode.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final JWTUtil jwtUtil;

    public ProductController(ProductService productService, JWTUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> registerProduct(
            @RequestBody ProductRegistrationRequest request) {
        productService.addProduct(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }
}
