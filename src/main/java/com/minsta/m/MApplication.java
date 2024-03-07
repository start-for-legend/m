package com.minsta.m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MApplication {

    public static void main(String[] args) {
        SpringApplication.run(MApplication.class, args);
    }

}
