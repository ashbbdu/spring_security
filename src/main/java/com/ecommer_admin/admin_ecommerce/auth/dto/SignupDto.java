package com.ecommer_admin.admin_ecommerce.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupDto {
    @NotNull(message = "Email is required !")
    @NotBlank
    private String email;
    @NotNull(message = "Password is required !")
    @NotBlank
    private String password;

    @NotNull(message = "Password is required !")
    @NotBlank
    private String name;
}
