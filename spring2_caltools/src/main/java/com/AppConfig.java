package com;


import com.mini.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration    //表示当前的类是一个配置类
@ComponentScan(basePackages = {"com.huawei", "com.mini"}) //将来要托管的bean要扫描的包及子包
public class AppConfig {  //java容器的配置

    @Bean   //beanid: "r"
    public Random r() {
        return new Random();
    }


    @Bean
    public Person p1() {
        return new Person("张三", 1.70, 80);
    }

    @Bean
    public Person p2() {
        return new Person("李四", 1.70, 60);
    }
}
