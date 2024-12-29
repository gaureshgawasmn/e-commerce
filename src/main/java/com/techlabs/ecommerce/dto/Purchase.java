package com.techlabs.ecommerce.dto;

import com.techlabs.ecommerce.entity.Address;
import com.techlabs.ecommerce.entity.Customer;
import com.techlabs.ecommerce.entity.Order;
import com.techlabs.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
