package com.ecommer_admin.admin_ecommerce.common.configs;

import com.ecommer_admin.admin_ecommerce.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->

                        auth
                                .requestMatchers("/products/**" , "/product-client/**" , "/auth/**").permitAll()
                                .requestMatchers("/brands/**").authenticated()
//                                .requestMatchers("/brands/**").hasAllRoles("ADMIN" , "USER")
                                .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/index.html"))
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)

        ;
        return http.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUser () {
//        UserDetails adminUser = User.withUsername("Ashish").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
//        UserDetails normalUser = User.withUsername("Ashish1").password(passwordEncoder().encode("xyz")).roles("USER").build();
//        return new InMemoryUserDetailsManager(adminUser , normalUser);
//    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
