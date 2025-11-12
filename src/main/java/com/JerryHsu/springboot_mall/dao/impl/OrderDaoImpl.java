package com.JerryHsu.springboot_mall.dao.impl;

import com.JerryHsu.springboot_mall.dao.OrderDao;
import com.JerryHsu.springboot_mall.dao.dto.CreateOrderRequest;
import com.JerryHsu.springboot_mall.dao.dto.OrderQueryParms;
import com.JerryHsu.springboot_mall.dao.dto.ProductQueryParms;
import com.JerryHsu.springboot_mall.model.Order;
import com.JerryHsu.springboot_mall.model.OrderItem;
import com.JerryHsu.springboot_mall.rowmapper.OrderItemRowMapper;
import com.JerryHsu.springboot_mall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Integer createOrder(Integer userId, Integer totalAmount) {

        String sql = " INSERT INTO torder( " +
                     " user_id, " +
                     " total_amount, " +
                     " created_date, " +
                     " last_modified_date " +
                     " ) VALUES (" +
                     " :userId, " +
                     " :totalAmount, " +
                     " :createdDate, " +
                     " :lastModifiedDate " +
                     " ) ";
        Map<String, Object> map = new HashMap<>();
        Date now = new Date();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);
        map.put("createdDate",now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder) ;

        Integer orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        //for 逐筆 update 方式
//        for (OrderItem orderItem : orderItemList){
//            String sql = " INSERT INTO torder_item( " +
//                    " order_id, " +
//                    " product_id, " +
//                    " quantity, " +
//                    " amount " +
//                    " ) VALUES (" +
//                    " :orderId, " +
//                    " :productId, " +
//                    " :quantity, " +
//                    " :amount "
//                    " ) ";
//
//            Map<String , Object> map = new HashMap<>();
//            map.put("orderId",orderId);
//            map.put("productId",orderItem.getProductId());
//            map.put("quantity",orderItem.getQuantity());
//            map.put("amount",orderItem.getAmount());
//
//            namedParameterJdbcTemplate.update(sql,map);
//        }

        //batch update 方式
        String sql = " INSERT INTO torder_item( " +
                " order_id, " +
                " product_id, " +
                " quantity, " +
                " amount " +
                " ) VALUES (" +
                " :orderId, " +
                " :productId, " +
                " :quantity, " +
                " :amount " +
                " ) ";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0 ; i < orderItemList.size(); i++ ){
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId",orderId);
            parameterSources[i].addValue("productId",orderItem.getProductId());
            parameterSources[i].addValue("quantity",orderItem.getQuantity());
            parameterSources[i].addValue("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = " SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                     "   FROM torder WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();

        map.put("orderId",orderId);

        List<Order> orderList= namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());

        if (orderList.size() > 0) {
            return orderList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsById(Integer orderId) {
        String sql = "SELECT o1.order_item_id," +
                "   o1.order_id," +
                "   o1.product_id," +
                "   o1.quantity," +
                "   o1.amount," +
                "   t.product_name," +
                "   t.image_url " +
                "  FROM torder_item o1" +
                "  JOIN tproduct t ON o1.product_id = t.product_id " +
                " WHERE o1.order_id = :orderId";
        Map<String,Object> map = new HashMap<>();

        map.put("orderId",orderId);

        List<OrderItem> orderList = namedParameterJdbcTemplate.query(sql,map, new OrderItemRowMapper());

        return orderList;
    }

    @Override
    public List<Order> getOrders(OrderQueryParms orderQueryParms) {
        String sql = " SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "   FROM torder WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql,map,orderQueryParms);

        sql = sql + " ORDER BY created_date DESC LIMIT :limit OFFSET :offset";

        map.put("limit",orderQueryParms.getLimit());
        map.put("offset",orderQueryParms.getOffset());

        List<Order> orderList= namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());

        if (orderList.size() > 0) {
            return orderList;
        }else{
            return null;
        }
    }

    @Override
    public Integer countOrder(OrderQueryParms orderQueryParms) {
        String sql = " select count(*)  FROM torder t WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();
        sql = addFilteringSql(sql,map,orderQueryParms);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    private String addFilteringSql(String sql , Map<String,Object> map, OrderQueryParms orderQueryParms){

        if (orderQueryParms.getUserId()  != null){
            sql = sql + " AND user_id = :userId";
            map.put("userId", orderQueryParms.getUserId());
        }
        return sql;
    }
}
