package com.ecommer_admin.admin_ecommerce.auth.dto;


import lombok.Data;

@Data
public class UserDto {
    private String email;

    private String password;

    private String name;
}
