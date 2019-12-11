package com.bos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@SpringBootApplication
public class SpringBootBosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBosApplication.class,args);
    }

    /**
     * 解决jpa的懒加载session丢失
     * @return
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }
}
