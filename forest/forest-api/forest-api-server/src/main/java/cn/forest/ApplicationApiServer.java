package cn.forest;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"cn.forest.mall.mapper","cn.forest.system.mapper"})
public class ApplicationApiServer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationApiServer.class, args);
    }
}
