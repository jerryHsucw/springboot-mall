package com.JerryHsu.springboot_mall.model;

import lombok.Data;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
}
