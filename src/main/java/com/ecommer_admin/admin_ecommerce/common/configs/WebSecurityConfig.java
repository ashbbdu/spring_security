package com.ecommer_admin.admin_ecommerce.common.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->

                        auth
                                .requestMatchers("/products/**" , "/auth/**").permitAll()
                                .requestMatchers("/brands/**").hasAnyRole("ADMIN")
//                                .requestMatchers("/brands/**").hasAllRoles("ADMIN" , "USER")
                                .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/index.html"))
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(Customizer.withDefaults())

        ;
        return http.build();
    }

    @Bean
    UserDetailsService myInMemoryUser () {
        UserDetails adminUser = User.withUsername("Ashish").password(passwordEncoder().encode("abc")).roles("ADMIN").build();
        UserDetails normalUser = User.withUsername("Ashish1").password(passwordEncoder().encode("xyz")).roles("USER").build();
        return new InMemoryUserDetailsManager(adminUser , normalUser);
    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
