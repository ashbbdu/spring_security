package com.ecommer_admin.admin_ecommerce.auth.controller;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String token;
}
