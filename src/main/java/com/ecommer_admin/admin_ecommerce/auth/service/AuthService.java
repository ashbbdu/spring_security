package com.ecommer_admin.admin_ecommerce.auth.service;

import com.ecommer_admin.admin_ecommerce.auth.controller.UserDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
}
