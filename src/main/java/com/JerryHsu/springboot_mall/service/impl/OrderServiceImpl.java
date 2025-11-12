package com.JerryHsu.springboot_mall.service.impl;

import com.JerryHsu.springboot_mall.dao.OrderDao;
import com.JerryHsu.springboot_mall.dao.ProductDao;
import com.JerryHsu.springboot_mall.dao.UserDao;
import com.JerryHsu.springboot_mall.dao.dto.BuyItem;
import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.dao.dto.OrderQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.model.Order;
import com.JerryHsu.springboot_mall.model.OrderItem;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.model.User;
import com.JerryHsu.springboot_mall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        //檢查user是否存在
        User user = userDao.getUserById(userId);
        if (user == null){
            log.warn("該user{}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        //用迴圈把ButItemList內的buyItem逐筆取出
        for (BuyItem buyItem: createOrderRequest.getBuyItemList()){

            Product product = productDao.getProductById(buyItem.getProductId());

            if (product == null){
                log.warn("商品{}不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            }else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品{}庫存不足，無法購買，剩餘庫存{}，欲購買{}",buyItem.getProductId(),
                product.getStock(),buyItem.getQuantity());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            Integer stock1 = product.getStock()- buyItem.getQuantity();
            //扣除商品庫存
            productDao.updateStock(product.getProductId(),stock1);

            //取出buyItem內對應商品資料
            //Product product = productDao.getProductById(buyItem.getProductId());
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
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        List<Order> orderList = orderDao.getOrders(orderQueryParms);

        for (Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsById(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {

        Integer count = orderDao.countOrder(orderQueryParms);
        return count;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }


}
