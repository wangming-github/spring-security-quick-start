package com.maizi.serve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author maizi
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {//
        "com.maizi.common.*",// 扫描公共模块的Bean
        "com.maizi.serve.*"// 扫描当前模块的Bean
})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
