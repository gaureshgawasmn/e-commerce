package com.techlabs.ecommerce.service;

import com.techlabs.ecommerce.dto.Purchase;
import com.techlabs.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
