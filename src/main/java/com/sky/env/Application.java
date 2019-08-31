package com.sky.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 */
@Import(PropertySourceDetectorConfiguration.class)
@SpringBootApplication
public class Application {

    public static void main(String [] args){
        SpringApplication.run(Application.class);
    }


}
