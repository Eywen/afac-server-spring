package com.tfm.afac;

import com.tfm.afac.data.daos.UserRepository;
import com.tfm.afac.data.model.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AfacApplication {

	public static void main(String[] args) {

		SpringApplication.run(AfacApplication.class, args);
		System.out.println("pass encript: "+new BCryptPasswordEncoder().encode("secreto"));
		System.out.println("pass encript: ");

	}

}
