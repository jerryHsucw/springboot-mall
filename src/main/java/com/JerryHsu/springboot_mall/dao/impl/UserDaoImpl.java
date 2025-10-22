package com.JerryHsu.springboot_mall.dao.impl;

import com.JerryHsu.springboot_mall.dao.UserDao;
import com.JerryHsu.springboot_mall.dao.dto.UserRegisterRequest;
import com.JerryHsu.springboot_mall.model.User;
import com.JerryHsu.springboot_mall.rowmapper.UserRowsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO mall.tuser (email, password, created_date, last_modified_date)"+
                     "VALUES(:email,:password,:createdDate,:lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map),keyHolder);

        Integer userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserEmail(String email) {
        String sql = " SELECT user_id, " +
                "        email, " +
                "        password," +
                "        created_date," +
                "        last_modified_date" +
                "   FROM mall.tuser " +
                "  WHERE email = :email";

        Map<String , Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowsMapper());

        if (userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getUserById(Integer userId) {

        String sql = " SELECT user_id, " +
                     "        email, " +
                     "        password," +
                     "        created_date," +
                     "        last_modified_date" +
                     "   FROM mall.tuser " +
                     "  WHERE user_id = :userId";

        Map<String , Object> map = new HashMap<>();
        map.put("userId",userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowsMapper());

        if (userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }



    }
}
