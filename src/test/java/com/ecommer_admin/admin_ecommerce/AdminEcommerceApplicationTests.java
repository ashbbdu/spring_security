package com.ecommer_admin.admin_ecommerce;

import com.ecommer_admin.admin_ecommerce.user.entities.UserEntity;
import com.ecommer_admin.admin_ecommerce.user.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminEcommerceApplicationTests {
	@Autowired
	JwtService jwtService;
	@Test
	void contextLoads() {
		UserEntity user = new UserEntity(4L , "ash@gmail.com" , "1234");
		String token = jwtService.generateToken(user);
		System.out.println(token + " token");

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id  + " user id");
	}



}
