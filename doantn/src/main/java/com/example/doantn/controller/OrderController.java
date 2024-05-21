package com.example.doantn.controller;

import com.example.doantn.dto.OrderRequest;
import com.example.doantn.entity.Order;
import com.example.doantn.entity.OrderItem;
import com.example.doantn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequestDTO) {
        try {
            Order order = orderService.createOrder(orderRequestDTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/listOrder/{userId}")
    public List<Order> getAllOrders(@PathVariable Long userId) {
        return orderService.getAllOrdersForUser(userId);
    }

    @GetMapping("/byOrder/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/increaseStatus/{orderId}")
    public ResponseEntity<Order> increaseOrderStatus(@PathVariable Long orderId) {
        Order updatedOrder = orderService.increaseOrderStatus(orderId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @GetMapping("/status/0")
    public ResponseEntity<List<Order>> getOrdersWithStatusZero() {
        List<Order> orders = orderService.getOrdersWithStatusZero();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/not-0")
    public ResponseEntity<List<Order>> getOrdersWithStatusNotZero() {
        List<Order> orders = orderService.getOrdersWithStatusNotZero();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/between-1-and-3")
    public ResponseEntity<List<Order>> getOrdersWithStatusBetweenOneAndThree() {
        List<Order> orders = orderService.getOrdersWithStatusBetweenOneAndThree();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/outside-1-to-3")
    public ResponseEntity<List<Order>> getOrdersWithStatusOutsideOneToThree() {
        List<Order> orders = orderService.getOrdersWithStatusOutsideOneToThree();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/status/0")
    public ResponseEntity<List<Order>> getOrdersWithStatusZero(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersWithStatusZero(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/status/between-1-and-3")
    public ResponseEntity<List<Order>> getOrdersWithStatusBetweenOneAndThree(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersWithStatusBetweenOneAndThree(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/status/outside-1-to-3")
    public ResponseEntity<List<Order>> getOrdersWithStatusOutsideOneToThree(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersWithStatusOutsideOneToThree(userId);
        return ResponseEntity.ok(orders);
    }


}
