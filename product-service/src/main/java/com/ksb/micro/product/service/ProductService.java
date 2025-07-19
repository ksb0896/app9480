package com.ksb.micro.product.service;

import com.ksb.micro.product.dto.ProductDto;
import com.ksb.micro.product.model.Product;
import com.ksb.micro.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private static final String PRODUCT_CACHE = "PRODUCT_CACHE";
    private final ProductRepository productRepository;

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto createProduct(ProductDto productDto){
        var product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());

        Product saveProduct = productRepository.save(product);
/*        Product product = Product.builder().name(productRequest.name()).description(productRequest.description()).price(productRequest.price()).build();
        productRepository.save(product);*/
        log.info("Product created!!");
        return  new ProductDto(saveProduct.getId(),saveProduct.getName(),saveProduct.getDescription(),saveProduct.getPrice());
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#productId")
    public ProductDto getProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productId));
        return new ProductDto(product.getId(), product.getName(),product.getDescription(), product.getPrice());
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDto updateProduct(ProductDto productDto){
        Long productId = Long.valueOf(productDto.id());
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("Cannot find product with id " + productId));

        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());

        Product updateProduct = productRepository.save(product);
        return new ProductDto(updateProduct.getId(), updateProduct.getName(), updateProduct.getDescription(), updateProduct.getPrice());
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#productId")
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

/*    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice())).toList();
    }*/
}
