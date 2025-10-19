package com.JerryHsu.springboot_mall.dao.dto;

import com.JerryHsu.springboot_mall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParms {

    private ProductCategory productCategory;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;

}
