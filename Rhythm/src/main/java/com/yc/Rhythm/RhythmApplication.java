package com.yc.Rhythm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yc.Rhythm.controller.AuthController;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.enums.ERole;
import com.yc.Rhythm.repository.RoleRepository;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class RhythmApplication {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	public static void main(String[] args) {
		SpringApplication.run(RhythmApplication.class, args);
	}


	@Bean
    public CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
                roleRepository.save(new Role(ERole.ROLE_USER));

                logger.info("Roles have been inserted into the database.");
            }
        };
    }

}
