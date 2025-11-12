package com.JerryHsu.springboot_mall.dao;

import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.dao.dto.OrderQueryParms;
import com.JerryHsu.springboot_mall.model.Order;
import com.JerryHsu.springboot_mall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer orderId, Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsById(Integer orderId);
    List<Order> getOrders(OrderQueryParms orderQueryParms);
    Integer countOrder(OrderQueryParms orderQueryParms);
}
