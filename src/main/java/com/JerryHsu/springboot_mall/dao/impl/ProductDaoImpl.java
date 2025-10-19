package com.JerryHsu.springboot_mall.dao.impl;

import com.JerryHsu.springboot_mall.constant.ProductCategory;
import com.JerryHsu.springboot_mall.dao.ProductDao;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductRequest;
import com.JerryHsu.springboot_mall.model.Product;
import com.JerryHsu.springboot_mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao  {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParms productQueryParms) {
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
                " WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        if (productQueryParms.getProductCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",productQueryParms.getProductCategory().name());
            //這樣資料庫就會固定存 FOOD、BOOK、CAR，
            //而不會因為未來 enum 改寫 toString() 而變成中文或其他描述。
            //name() >> 會呈現英文
            //toString >> 如果enums有override to_string 則會是中文

        }

        if (productQueryParms.getSearch()  != null){
            sql = sql + " AND product_name like :search";
            map.put("search", "%"+productQueryParms.getSearch()+"%"); //like 的% 只能放在map內
        }

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0){
            return productList;
        }
        else{
            return null;
        }
    }

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

    public Integer createProduct(ProductRequest productRequest){
        String sql  =   "INSERT INTO mall.tproduct (" +
                        "product_name, " +
                        "category, " +
                        "image_url, " +
                        "price, " +
                        "stock, " +
                        "description, " +
                        "created_date, " +
                        "last_modified_date" +
                        ") " +
                        "VALUES (" +
                        ":productName, " +
                        ":category, " +
                        ":imageUrl, " +
                        ":price, " +
                        ":stock, " +
                        ":description, " +
                        ":createdDate, " +
                        ":lastModifiedDate" +
                        ")";

        Map<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder(); //用來取得key值 參考章節5.5

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        Integer productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "UPDATE mall.tproduct set " +
                     "  product_name = :productName," +
                     "  category     = :category," +
                     "  image_url    = :imageUrl," +
                     "  price   = :price," +
                     "  stock   = :stock," +
                     "  description  = :description," +
                     "  last_modified_date = :lastModifiedDate" +
                     "  WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();

        map.put("productId",productId);
        map.put("productName",productRequest.getProductName());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("category",productRequest.getCategory().toString());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("lastModifiedDate",now);

        namedParameterJdbcTemplate.update(sql,map);

        }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM mall.tproduct WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();

        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }
}
