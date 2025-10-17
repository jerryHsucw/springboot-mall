package com.JerryHsu.springboot_mall.dao.impl;

import com.JerryHsu.springboot_mall.dao.ProductDao;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao  {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {

        String sql = "select t.product_id , " +
                "t.product_name , " +
                "t.category , " +
                "t.image_url , " +
                "t.price ," +
                "t.stock ," +
                "t.description ," +
                "t.created_date , " +
                "t.last_modified_date " +
                "from mall.tproduct t " +
                "where t.product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0){
            return productList.get(0);
        }
        else{
            return null;
        }

    }
}
