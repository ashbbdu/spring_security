package com.ecommer_admin.admin_ecommerce.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginViewDto extends UserDto {
    private String token;
}
