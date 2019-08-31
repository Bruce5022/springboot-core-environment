package com.sky.env.modle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${app.name}")
    String name;
    @Value("${app.age}")
    Integer age;
    @Autowired
    ConfigurableEnvironment configurableEnvironment;

    @Override
    public void run(String... args) throws Exception {
        log.info("name = {},age = {}", name, age);
    }
}