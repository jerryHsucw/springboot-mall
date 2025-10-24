package com.JerryHsu.springboot_mall.service;

import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
