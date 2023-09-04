package ru.javaops.bootjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class VoteForRestaurantApp {

    public static void main(String[] args) {
        SpringApplication.run(VoteForRestaurantApp.class, args);
    }
}
