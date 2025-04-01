package com.oms.ordermanager.services;

import com.oms.ordermanager.dtos.OrderItemDTO;
import com.oms.ordermanager.dtos.OrderRequestDTO;
import com.oms.ordermanager.entities.Order;
import com.oms.ordermanager.entities.OrderItem;
import com.oms.ordermanager.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderValidationService validationService;
    @Autowired
    private PaymentService paymentService;
    private final RestTemplate restTemplate = new RestTemplate();
    public Order createOrder(OrderRequestDTO orderRequest) {

        // Step 1: Validate business rules
        validationService.validateOrder(orderRequest);
        double totalPrice = 0.0;
        for (OrderItemDTO orderItemDTO : orderRequest.getOrderItems()) {
            totalPrice += orderItemDTO.getPrice() * orderItemDTO.getQuantity();
        }
        // Step 2: Verify payment
        if (!paymentService.validatePayment(orderRequest.getPaymentId(), totalPrice)) {
            throw new IllegalArgumentException("Payment validation failed!");
        }
        // Step 3: Persist order
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setCustomerAddress(orderRequest.getShippingAddress());
        order.setItems(orderRequest.getOrderItems().stream()
                                    .map(orderItemDTO -> {
                                        OrderItem orderItem = new OrderItem();
                                        orderItem.setSku(orderItemDTO.getSku());
                                        orderItem.setQuantity(orderItemDTO.getQuantity());
                                        orderItem.setPrice(orderItemDTO.getPrice());
                                        orderItem.setProductName(orderItemDTO.getProductName());
                                        orderItem.setWeight(orderItemDTO.getWeight());
                                        orderItem.setLength(orderItemDTO.getLength());
                                        orderItem.setHeight(orderItemDTO.getHeight());
                                        orderItem.setWidth(orderItemDTO.getWidth());
                                        return orderItem;
                                    })
                                    .toList());
        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING");
        String res= String.valueOf(restTemplate.postForEntity("http://localhost:8083/order/create",order,String.class));
        return orderRepository.save(order);
    }
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
    public List<Order> getUserOrders(String customerId){
        return orderRepository.findByCustomerId(customerId);
    }
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    @Scheduled(fixedRate = 5000)
    public void updateOrders(){
        HashMap<String,String> statuses = restTemplate.getForEntity("http://localhost:8083/order/update-status", HashMap.class).getBody();
        List<Order> order = orderRepository.findAll();
        order.forEach((o)->o.setStatus(statuses.get(o.getId())));
        orderRepository.saveAll(order);
    }
}

