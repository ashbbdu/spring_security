package com.ecommer_admin.admin_ecommerce.filters;

import com.ecommer_admin.admin_ecommerce.auth.service.JwtService;
import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String requestTokenHeaders = request.getHeader("Authorization");

            if (requestTokenHeaders == null || !requestTokenHeaders.startsWith("Bearer")) {
                filterChain.doFilter(request , response);
                return;
            }

            String token = request.getHeader("Authorization").split("Bearer ")[1];
            System.out.println(token + " token from headers");

            Long userId = jwtService.getIdFromToken(token);
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity user = userService.getUserById(userId);

//            Create an authentication

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user , null , null
                );

                SecurityContextHolder.getContext().setAuthentication(authentication); // here we have to pass the authentication
            }
            filterChain.doFilter(request , response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request , response , null , ex);
        }
    }
}
