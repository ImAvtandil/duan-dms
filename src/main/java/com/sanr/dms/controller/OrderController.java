package com.sanr.dms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanr.dms.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/read-committed")
    public ResponseEntity<String> createOrderWithReadCommitted(@RequestParam String orderName) {
        orderService.createOrderWithReadCommitted(orderName);
        return new ResponseEntity<>("Order created using Read Committed", HttpStatus.CREATED);
    }

    @PostMapping("/create/repeatable-read")
    public ResponseEntity<String> createOrderWithRepeatableRead(@RequestParam String orderName) {
        orderService.createOrderWithRepeatableRead(orderName);
        return new ResponseEntity<>("Order created using Repeatable Read", HttpStatus.CREATED);
    }
}
