package com.JerryHsu.springboot_mall.rowmapper;

import com.JerryHsu.springboot_mall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {

        Product tproduct = new Product();
        tproduct.setProductId(resultSet.getInt("product_id"));
        tproduct.setProductName(resultSet.getString("product_name"));
        tproduct.setCategory(resultSet.getString("category"));
        tproduct.setImageUrl(resultSet.getString("image_url"));
        tproduct.setPrice(resultSet.getInt("price"));
        tproduct.setStock(resultSet.getInt("stock"));
        tproduct.setDescription(resultSet.getString("description"));
        tproduct.setCreatedDate(resultSet.getDate("created_date"));
        tproduct.setLastModifiedDate(resultSet.getDate("last_modified_date"));

        return tproduct;
    }
}
