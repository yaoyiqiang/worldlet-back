package com.yyq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yyq.dao")
public class worldletApplication {
    public static void main(String[] args) {

        SpringApplication.run(worldletApplication.class,args);
    }
}
