package com.ecommer_admin.admin_ecommerce.auth.service;

import com.ecommer_admin.admin_ecommerce.auth.controller.UserDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.LoginDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDto signup (SignupDto signupDto) {
        Optional<UserEntity> user = userRepository.findByEmail(signupDto.getEmail());

        if(user.isPresent()) {
            throw new ConflictException("User already present");
        }

        UserEntity userToCreate = modelMapper.map(signupDto , UserEntity.class);

        userToCreate.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        UserEntity savedUser = userRepository.save(userToCreate);


        String token = jwtService.generateToken(savedUser);
        UserDto userDto = modelMapper.map(savedUser , UserDto.class);
        userDto.setToken(token);


        return userDto;


    }


    public UserDto login(LoginDto loginDto) {
//        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() ->
//                     new ResourceNotFoundException("User not found for the given email")); // this is not required because we are already handling this in UserService

        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginDto.getEmail() , loginDto.getPassword()) // passing the principle
        );

        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();
        assert authenticatedUser != null;
        String token = jwtService.generateToken(authenticatedUser);
        UserDto userDto =  modelMapper.map(authenticatedUser , UserDto.class);;

        userDto.setToken(token);
        return userDto;
    }
}
