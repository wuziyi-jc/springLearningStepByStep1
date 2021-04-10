package com.yc;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration     //表示当前的类是一个配置类
@ComponentScan(basePackages = {"com.yc"})   //表示当前的类是一个配置类
@EnableAspectJAutoProxy   //启用aspectJ支持
public class MyAppConfig {   //java容器的配置
}
