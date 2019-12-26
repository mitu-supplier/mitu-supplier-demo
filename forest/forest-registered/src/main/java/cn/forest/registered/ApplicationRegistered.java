package cn.forest.registered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ApplicationRegistered {
    public static void main( String[] args ){
        SpringApplication.run(ApplicationRegistered.class, args);
    }
}
