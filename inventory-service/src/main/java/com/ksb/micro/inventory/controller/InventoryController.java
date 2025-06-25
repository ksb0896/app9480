package com.ksb.micro.inventory.controller;

import com.ksb.micro.inventory.service.InventoryService;
/*import lombok.RequiredArgsConstructor;*/
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
/*@RequiredArgsConstructor*/
public class InventoryController {
    private final InventoryService inventoryService;

    //constructor manually added!!!
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }
}
