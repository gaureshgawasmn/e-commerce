package com.techlabs.ecommerce.service;

import com.techlabs.ecommerce.dao.CustomerRepository;
import com.techlabs.ecommerce.dto.Purchase;
import com.techlabs.ecommerce.dto.PurchaseResponse;
import com.techlabs.ecommerce.entity.Customer;
import com.techlabs.ecommerce.entity.Order;
import com.techlabs.ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::addItem);

        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        Customer customer = purchase.getCustomer();
        customer.addOrder(order);

        customerRepository.save(customer);

        return PurchaseResponse.builder().orderTrackingNumber(orderTrackingNumber).build();
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
