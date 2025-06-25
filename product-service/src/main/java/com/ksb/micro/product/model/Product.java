package com.ksb.micro.product.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
