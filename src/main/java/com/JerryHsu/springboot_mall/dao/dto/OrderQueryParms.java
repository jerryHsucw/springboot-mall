package com.JerryHsu.springboot_mall.dao.dto;

import lombok.Data;

@Data
public class OrderQueryParms
{
    private Integer userId;
    private Integer limit;
    private Integer offset;
}
