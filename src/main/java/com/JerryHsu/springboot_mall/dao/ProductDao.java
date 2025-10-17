package com.JerryHsu.springboot_mall.dao;

import com.JerryHsu.springboot_mall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
