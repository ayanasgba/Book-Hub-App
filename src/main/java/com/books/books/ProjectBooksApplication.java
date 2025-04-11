package com.books.books;

import com.books.books.enums.Roles;
import com.books.books.models.User;
import com.books.books.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBooksApplication.class, args);
	}

	@Bean
	public CommandLineRunner addAdmin(UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			if (!userRepository.existsByUsername("admin")) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setEmail("admin@example.com");
				admin.setPassword(encoder.encode("admin123"));
				admin.setRoles(Roles.ADMIN);
				userRepository.save(admin);
			}
		};
	}

}
