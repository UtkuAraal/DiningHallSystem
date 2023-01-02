package com.UB.DiningHallSystem;

import com.UB.DiningHallSystem.entity.AppUser;
import com.UB.DiningHallSystem.entity.Role;
import com.UB.DiningHallSystem.service.UserService;
import com.UB.DiningHallSystem.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DiningHallSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningHallSystemApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService){
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_STUDENT"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new AppUser(null, "John Travolta", "john", "1234", new ArrayList<>(), 0));
			userService.saveUser(new AppUser(null, "Will Smith", "will", "1234", new ArrayList<>(), 0));
			userService.saveUser(new AppUser(null, "Jim Carrey", "jim", "1234", new ArrayList<>(), 0));
			userService.saveUser(new AppUser(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>(), 0));

			userService.addRoleToUser("john", "ROLE_STUDENT");
			userService.addRoleToUser("will", "ROLE_MANAGER");
			userService.addRoleToUser("jim", "ROLE_STUDENT");
			userService.addRoleToUser("jim", "ROLE_MANAGER");
			userService.addRoleToUser("arnold", "ROLE_ADMIN");



		};
	}

}
