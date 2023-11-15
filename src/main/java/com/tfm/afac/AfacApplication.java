package com.tfm.afac;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AfacApplication {

	public static void main(String[] args) {

		SpringApplication.run(AfacApplication.class, args);
		System.out.println("pass encript: "+new BCryptPasswordEncoder().encode("secreto"));
	}

}
