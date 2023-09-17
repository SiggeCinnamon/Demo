package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class CurlOnStartup implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) {
        String curlCommand1 = "curl -X POST http://localhost:8080/JourneyPatternPointOnLine";
        String curlCommand2 = "curl -X POST http://localhost:8080/site";
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            Future<?> future = executorService.submit(() -> {
                try {
                    Process process = Runtime.getRuntime().exec(curlCommand1);
                    System.out.println(curlCommand1);
                    process.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(5000);
            Future<?> future2 = executorService.submit(() -> {
                try {
                    Process process2 = Runtime.getRuntime().exec(curlCommand2);
                    System.out.println(curlCommand2);
                    process2.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            future.get();
            future2.get();
            executorService.shutdown();
            System.out.println("Shutdown");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}