package com.lab4tech.hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@EntityScan("com.lab4tech.hive.model.entity")
@SpringBootApplication
public class HiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiveApplication.class, args);
	}

}
