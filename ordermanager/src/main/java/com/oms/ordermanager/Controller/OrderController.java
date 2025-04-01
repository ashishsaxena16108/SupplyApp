package com.oms.ordermanager.Controller;

import com.oms.ordermanager.dtos.OrderRequestDTO;
import com.oms.ordermanager.entities.Order;
import com.oms.ordermanager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        // Business logic here (e.g., inventory check)
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Order successfully validated!");
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String customerId){
        return ResponseEntity.ok(orderService.getUserOrders(customerId));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
