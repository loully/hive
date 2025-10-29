package com.lab4tech.hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class HiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiveApplication.class, args);

		List<Integer> data = new ArrayList<>();
		IntStream.range(0,100).parallel().forEach(s -> {
				synchronized(data){
				data.add(s);
		}});
		System.out.println(data.size());
	}

}
