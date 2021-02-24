package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

import lombok.Data;

@SpringBootApplication( exclude={
		RedisRepositoriesAutoConfiguration.class  // To persist session on redis we do not need Redis Repositories
})

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
