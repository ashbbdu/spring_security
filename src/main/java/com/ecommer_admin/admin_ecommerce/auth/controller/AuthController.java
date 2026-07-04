package com.ecommer_admin.admin_ecommerce.auth.controller;

import com.ecommer_admin.admin_ecommerce.auth.dto.LoginDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.LoginViewDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.UserDto;
import com.ecommer_admin.admin_ecommerce.auth.service.AuthService;
import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup (@RequestBody SignupDto signupDto) {
       UserDto user = userService.signUp(signupDto);
       return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginViewDto> login (@RequestBody LoginDto loginDto , HttpServletRequest request , HttpServletResponse response) {
       LoginViewDto user = authService.login(loginDto , request , response);
       return ResponseEntity.ok(user);
    }

}
