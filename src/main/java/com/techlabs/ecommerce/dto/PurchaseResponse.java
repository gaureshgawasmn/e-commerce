package com.techlabs.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseResponse {
    private String orderTrackingNumber;
}
