package com.JerryHsu.springboot_mall.controller;

import com.JerryHsu.springboot_mall.constant.ProductCategory;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.service.ProductService;
import com.JerryHsu.springboot_mall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Valid
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")//對於RESTful API的Mapping說明，可以再參考4-11 / 4-7@RequestParam
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序條件 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, //可參考4-7
            @RequestParam(defaultValue = "desc") String sort,
            //分頁
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, //max / min 可以參考4-13
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        ProductQueryParms productQueryParms = new ProductQueryParms();
        productQueryParms.setProductCategory(category);
        productQueryParms.setSearch(search);
        productQueryParms.setOrderBy(orderBy);
        productQueryParms.setSort(sort);
        productQueryParms.setLimit(limit);
        productQueryParms.setOffset(offset);

        List<Product> productsList = productService.getProducts(productQueryParms);

        Integer total = productService.countProduct(productQueryParms);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productsList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

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

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        //檢查productId是否存在
        Product queryProduct = productService.getProductById(productId);
        if (queryProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //刪除資料
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
