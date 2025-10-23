package com.JerryHsu.springboot_mall.dao.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

//可以參考4-4 巢狀 與 4-3 NotEmpty
@Data
public class CreateOrderRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;
}
