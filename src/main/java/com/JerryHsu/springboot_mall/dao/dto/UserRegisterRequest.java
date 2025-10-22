package com.JerryHsu.springboot_mall.dao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
