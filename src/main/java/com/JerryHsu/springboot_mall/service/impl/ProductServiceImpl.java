package com.JerryHsu.springboot_mall.service.impl;

import com.JerryHsu.springboot_mall.constant.ProductCategory;
import com.JerryHsu.springboot_mall.dao.ProductDao;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParms productQueryParms) {
        return productDao.getProducts(productQueryParms);
    }

    @Override
    public Integer countProduct(ProductQueryParms productQueryParms) {
        return productDao.countProduct(productQueryParms);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
