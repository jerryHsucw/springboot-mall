package com.JerryHsu.springboot_mall.controller;

import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.dao.dto.OrderQueryParms;
import com.JerryHsu.springboot_mall.model.Order;
import com.JerryHsu.springboot_mall.service.OrderService;
import com.JerryHsu.springboot_mall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<?> getOrders(@PathVariable Integer userId,
                                       //分頁
                                       @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit, //max / min 可以參考4-13
                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        OrderQueryParms orderQueryParms = new OrderQueryParms();
        orderQueryParms.setLimit(limit);
        orderQueryParms.setOffset(offset);
        orderQueryParms.setUserId(userId);

        //取得訂單明細
        List<Order> order = orderService.getOrders(orderQueryParms);

        //取得訂單總數
        Integer count = orderService.countOrder(orderQueryParms);

        Page<Order> page = new Page<>();
        page.setTotal(count);
        page.setOffset(offset);
        page.setLimit(count);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

}
