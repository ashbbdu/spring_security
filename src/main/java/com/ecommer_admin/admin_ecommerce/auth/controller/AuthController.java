package com.ecommer_admin.admin_ecommerce.auth.controller;

import com.ecommer_admin.admin_ecommerce.auth.dto.LoginDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> signup (@RequestBody SignupDto signupDto) {
        UserDto user = authService.signup(signupDto);
        return ResponseEntity.ok(user);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> login (@RequestBody LoginDto loginDto) {
        UserDto user = authService.login(loginDto);
        return ResponseEntity.ok(user);
    }
}
