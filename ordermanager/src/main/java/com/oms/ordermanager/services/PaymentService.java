package com.oms.ordermanager.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean validatePayment(String paymentId, double amount) {
        // Simulate a payment verification call (Replace with actual API logic)
        return true && amount > 0;
    }
}

