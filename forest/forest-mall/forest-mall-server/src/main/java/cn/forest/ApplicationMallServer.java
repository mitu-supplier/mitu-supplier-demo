package cn.forest;

import cn.forest.common.service.datasource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import javax.swing.*;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.forest.mall.mapper")
@Import(DynamicDataSourceRegister.class)
public class ApplicationMallServer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMallServer.class, args);
    }
}
