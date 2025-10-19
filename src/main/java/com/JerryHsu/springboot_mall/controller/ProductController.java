package com.JerryHsu.springboot_mall.controller;

import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        //將productRequest的資料，insert到table內，並回傳新增的那一筆資料的key value
        Integer productId = productService.createProduct(productRequest);

        //透過key value在反查一次該key value對應的資料
        Product product = productService.getProductById(productId);

        //會傳給前端有新增後查詢的結果
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        //檢查productId是否存在
        Product queryProduct = productService.getProductById(productId);
        if (queryProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //更新資料
        productService.updateProduct(productId, productRequest);

        Product updatedProdect = productService.getProductById(productId);
        //會傳給前端有新增後查詢的結果
        return ResponseEntity.status(HttpStatus.OK).body(updatedProdect);

    }


}
