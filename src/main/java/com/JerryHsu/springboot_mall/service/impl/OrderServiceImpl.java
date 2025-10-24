package com.JerryHsu.springboot_mall.service.impl;

import com.JerryHsu.springboot_mall.dao.OrderDao;
import com.JerryHsu.springboot_mall.dao.ProductDao;
import com.JerryHsu.springboot_mall.dao.dto.BuyItem;
import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.model.Order;
import com.JerryHsu.springboot_mall.model.OrderItem;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();


        //用迴圈把ButItemList內的buyItem逐筆取出
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()){
            //取出buyItem內對應商品資料
            Product product = productDao.getProductById(buyItem.getProductId());
            //計算商品金額 = buyItem的要購買的商品數 * product的金額
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }

        //建立訂單
        Integer orderId =  orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }
}
