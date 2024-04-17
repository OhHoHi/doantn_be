package com.example.doantn;

import com.example.doantn.entity.Role;
import com.example.doantn.entity.User;
import com.example.doantn.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class DoantnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoantnApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new Role(null , "ROLE_USER"));
//			userService.saveRole(new Role(null , "ROLE_ADMIN"));
//
//			userService.saveUser(new User(null, "Doan Xuan Quyet" ,"Xuan Quyet" ,"vanchuadau56@gmail.com" , "123456" , new HashSet<>()));
//			userService.saveUser(new User(null, "Doan Xuan Quy" ,"Xuan Quy" ,"quyokok@gmail.com" , "123456" , new HashSet<>()));

//			userService.addToUser("vanchuadau56@gmail.com" , "ROLE_ADMIN");
//			userService.addToUser("quyokok@gmail.com" , "ROLE_USER");
//
//
//		};
//	}

}
