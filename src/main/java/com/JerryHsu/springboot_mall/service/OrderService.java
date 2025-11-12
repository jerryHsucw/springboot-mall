package com.JerryHsu.springboot_mall.service;

import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.dao.dto.OrderQueryParms;
import com.JerryHsu.springboot_mall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    List<Order> getOrders(OrderQueryParms orderQueryParms);
    Integer countOrder(OrderQueryParms orderQueryParms);
}
