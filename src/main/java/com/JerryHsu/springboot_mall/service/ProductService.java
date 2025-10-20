package com.JerryHsu.springboot_mall.service;

import com.JerryHsu.springboot_mall.constant.ProductCategory;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import jakarta.validation.Valid;
import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParms productQueryParms);

    Integer countProduct(ProductQueryParms productQueryParms);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, @Valid ProductRequest productRequest);

    void deleteProduct(Integer productId);

}
