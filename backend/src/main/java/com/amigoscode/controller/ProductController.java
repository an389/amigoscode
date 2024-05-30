package com.amigoscode.controller;

import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.model.Product;
import com.amigoscode.model.api.ProductRegistrationRequest;
import com.amigoscode.model.dto.ProductDTO;
import com.amigoscode.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final JWTUtil jwtUtil;

    public ProductController(ProductService productService, JWTUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public String registerProduct(
            @RequestBody ProductRegistrationRequest request) {
        productService.addProduct(request);
        return "OK";
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Integer idProduct){
        return productService.getProduct(idProduct);
    }
}
