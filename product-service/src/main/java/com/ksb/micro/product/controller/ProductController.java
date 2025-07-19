package com.ksb.micro.product.controller;

import com.ksb.micro.product.dto.ProductDto;
import com.ksb.micro.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto){
    return productService.createProduct(productDto);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }
}
