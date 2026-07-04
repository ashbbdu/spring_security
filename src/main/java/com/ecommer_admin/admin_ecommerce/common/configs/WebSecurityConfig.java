package com.ecommer_admin.admin_ecommerce.common.configs;

import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth ->
                        auth.
                        requestMatchers("/brands/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/products/**").permitAll() // all the request with /posts are public
                                .requestMatchers("/products/**").hasAnyRole("USER")
                                .anyRequest().authenticated())
//                .formLogin(formLoginConfig -> formLoginConfig.permitAll())
                        .formLogin(Customizer.withDefaults())
//                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/pages/index.html")); // index .html can be configured in index.html
                ;
       return httpSecurity.build();
    }

    @Bean
    UserDetailsService inMemoryUserDetailsService () {
        UserDetails normalUser = User.withUsername("Ashish").password(passwordEncoder().encode("1234")).roles("USER").build();
        UserDetails adminUser = User.withUsername("Ash").password(passwordEncoder().encode("1234")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(normalUser , adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
