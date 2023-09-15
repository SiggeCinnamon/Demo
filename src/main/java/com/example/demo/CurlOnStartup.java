package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CurlOnStartup implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        String curlCommand = "curl -X POST http://localhost:8080/test1";
        try {
            Process process = Runtime.getRuntime().exec(curlCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}