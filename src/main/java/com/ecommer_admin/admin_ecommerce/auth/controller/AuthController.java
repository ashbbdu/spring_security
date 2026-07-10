package com.ecommer_admin.admin_ecommerce.auth.controller;

import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public UserDto signup (@RequestBody SignupDto signupDto) {
        return authService.signup(signupDto);
    }
}
