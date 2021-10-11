package com.lc.justddns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lc")
public class JustDdnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustDdnsApplication.class, args);
	}

}
