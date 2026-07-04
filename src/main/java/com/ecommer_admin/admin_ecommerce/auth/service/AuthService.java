package com.ecommer_admin.admin_ecommerce.auth.service;

import com.ecommer_admin.admin_ecommerce.auth.dto.LoginDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.LoginViewDto;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.repository.UserRepository;
import com.ecommer_admin.admin_ecommerce.user.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
//    private final LoginViewDto loginViewDto;

    public LoginViewDto login(LoginDto loginDto , HttpServletRequest request , HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User with this email does not exists !"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail() , loginDto.getPassword())
        );

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        assert currentUser != null;
        String token = jwtService.generateToken(currentUser);
        System.out.println(token + " user token");

        Cookie cookie = new Cookie("token" , token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

//        loginViewDto.setToken(token);

        LoginViewDto resp = modelMapper.map(currentUser, LoginViewDto.class);
        resp.setToken(token);
        return resp;


    }
}
