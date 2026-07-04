package com.ecommer_admin.admin_ecommerce.filters;

import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.service.JwtService;
import com.ecommer_admin.admin_ecommerce.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
            filterChain.doFilter(request , response);
            return;
        }

        String token = requestTokenHeader.split("Bearer")[1];
        Long userId = jwtService.getUserIdFromToken(token);

        if(userId != null) {

            UserEntity user = userService.getUserById(userId);
//              put user in Sprig Security Authentication Context Holder

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user , null , null);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

//        call the next filter
        filterChain.doFilter(request , response);
    }
}
