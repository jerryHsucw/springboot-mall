package com.JerryHsu.springboot_mall.service;

import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import jakarta.validation.Valid;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, @Valid ProductRequest productRequest);
}
