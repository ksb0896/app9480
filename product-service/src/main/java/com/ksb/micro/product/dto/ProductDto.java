package com.ksb.micro.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(Long id, @NotBlank String name, String description, @Positive BigDecimal price) {
}
