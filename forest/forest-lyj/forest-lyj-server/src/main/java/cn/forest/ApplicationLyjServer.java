package cn.forest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import cn.forest.common.service.datasource.DynamicDataSourceRegister;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.forest.lyj.mapper")
@Import(DynamicDataSourceRegister.class)
public class ApplicationLyjServer {
    public static void main( String[] args ){
        
      SpringApplication.run(ApplicationLyjServer.class, args);
         
    }
}
