package com.example.crud.controller;

import com.example.crud.dto.OrderDto;
import com.example.crud.model.Order;
import com.example.crud.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Order CRUD operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

   @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order(null, orderDto.getUserId(), orderDto.getStatus(), 
                               orderDto.getTotalAmount(), orderDto.getShippingAddress());
        Order saved = orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        if (!orderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Order order = new Order(id, orderDto.getUserId(), orderDto.getStatus(), 
                               orderDto.getTotalAmount(), orderDto.getShippingAddress());
        Order updated = orderService.save(order);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.findByStatus(status));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOrders() {
        return ResponseEntity.ok(orderService.count());
    }
}
