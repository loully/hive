package com.lab4tech.hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.lab4tech.hive.model.entity")
@SpringBootApplication
public class HiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiveApplication.class, args);
	}

}
