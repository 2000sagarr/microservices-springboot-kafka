package com.sagar.orderservice.controller;

import com.sagar.basedomain.dto.Order;
import com.sagar.basedomain.dto.OrderEvent;
import com.sagar.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderProducer  orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }


    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order event is in pending state");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully";
    }
}
