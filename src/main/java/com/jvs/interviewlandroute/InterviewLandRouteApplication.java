package com.jvs.interviewlandroute;

import com.jvs.interviewlandroute.clients.CountryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = CountryClient.class)
public class InterviewLandRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewLandRouteApplication.class, args);
    }

}
