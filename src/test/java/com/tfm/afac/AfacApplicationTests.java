package com.tfm.afac;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//@ExtendWith(SpringExtension.class)

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AfacApplicationTests.class)
//@TestPropertySource(locations = "classpath:test.properties")
//@ActiveProfiles("dev")
class AfacApplicationTests {

	@Test
	void contextLoads() {
	}

}
