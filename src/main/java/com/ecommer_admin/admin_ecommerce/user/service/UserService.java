package com.ecommer_admin.admin_ecommerce.user.service;

import com.ecommer_admin.admin_ecommerce.auth.dto.LoginDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.LoginViewDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.SignupDto;
import com.ecommer_admin.admin_ecommerce.auth.dto.UserDto;
import com.ecommer_admin.admin_ecommerce.common.exception.BadRequestException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User does not exists !"));
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found !"));
    }

    public UserDto signUp(SignupDto signupDto) {
        Optional<UserEntity> user = userRepository.findByEmail(signupDto.getEmail());

        if(user.isPresent()) {
            throw new BadRequestException("User already present , please use a different email or try to sign in");
        }

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        UserEntity createdUser = modelMapper.map(signupDto , UserEntity.class);
        UserEntity savedUser =  userRepository.save(createdUser);
        return modelMapper.map(savedUser , UserDto.class);

    }


}
