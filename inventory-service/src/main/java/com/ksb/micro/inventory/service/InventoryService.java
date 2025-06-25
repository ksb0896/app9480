package com.ksb.micro.inventory.service;

import com.ksb.micro.inventory.repository.InventoryRepository;
/*import lombok.RequiredArgsConstructor;*/
import org.springframework.stereotype.Service;

@Service
/*@RequiredArgsConstructor*/
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    //constructor manually added!!!
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isInStock(String skuCode, Integer quantity){
        //data JPA will create the query
       return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }
}
