package com.ksb.micro.order.service;

import com.ksb.micro.order.client.InventoryClient;
import com.ksb.micro.order.dto.OrderRequest;
import com.ksb.micro.order.event.OrderPlacedEvent;
import com.ksb.micro.order.model.Order;
import com.ksb.micro.order.repository.OrderRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
/*@RequiredArgsConstructor*/
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    //Manually addition of constructor
    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(isProductInStock){
            //map orderRequest to order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            //save order to orderRepository
            orderRepository.save(order);

            //send msg to kafka topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstname());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
            log.info("Start - Sending OrderPlacedEvent{} to kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End -  OrderPlacedEvent{}", orderPlacedEvent);
        }else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
        }
}
}
