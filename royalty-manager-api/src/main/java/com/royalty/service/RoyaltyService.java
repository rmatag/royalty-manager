package com.royalty.service;

import com.royalty.dto.Payment;
import org.springframework.stereotype.Service;

@Service
public class RoyaltyService {
    public Payment getPayments() {
        Payment payment = new Payment();
        payment.setRightOwner("Right Owner Name");
        payment.setRightOwnnerId("123-123-123");
        return payment;
    }
}
