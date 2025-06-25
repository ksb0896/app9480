package com.ksb.micro.order.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity, UserDetails userDetails) {

    public record UserDetails(String email, String firstname, String lastName){}
}
