package cn.forest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationFileWeb {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationFileWeb.class, args);
    }
}
