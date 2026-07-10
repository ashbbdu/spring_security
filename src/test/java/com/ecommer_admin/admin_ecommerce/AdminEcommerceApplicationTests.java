package com.ecommer_admin.admin_ecommerce;

import com.ecommer_admin.admin_ecommerce.auth.service.JwtService;
import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminEcommerceApplicationTests {

	@Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {
		UserEntity user = new UserEntity(1L , "ash@yopmail.com" , "abcde" , "ash" );
		String token = jwtService.generateToken(user);
		System.out.println(token + " JWT Token");
	}

}
