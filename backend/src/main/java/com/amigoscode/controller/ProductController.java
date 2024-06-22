package com.amigoscode.controller;

import com.amigoscode.jwt.JWTUtil;
import com.amigoscode.model.Product;
import com.amigoscode.model.api.ProductRegistrationRequest;
import com.amigoscode.model.dto.ProductDTO;
import com.amigoscode.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Integer registerProduct(
            @RequestBody ProductRegistrationRequest request) {
        //we return product id for the user to know the product id for upload picture
        return productService.addProduct(request);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Integer idProduct) {
        return productService.getProduct(idProduct);
    }

    @GetMapping("/search")
    public List<ProductDTO> getSearchProduct( @RequestParam("keyword") String keyword) {
        return productService.findBySearch(keyword);
    }

    @PostMapping(
            value = "{productId}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCustomerProfileImage(
            @PathVariable("productId") Integer productId,
            @RequestParam("file") MultipartFile file) {
        productService.uploadProductImage(productId, file);
    }

    @GetMapping(
            value = "{productId}/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getCustomerProfileImage(
            @PathVariable("productId") Integer productId) {
        return productService.getProductImage(productId);
    }
}
