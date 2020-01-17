package cn.forest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.forest.mall.mapper")
public class ApplicationMallServer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMallServer.class, args);
    }
}
