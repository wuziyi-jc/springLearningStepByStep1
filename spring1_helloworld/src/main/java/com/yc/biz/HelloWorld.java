package com.yc.biz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy   //懒加载 开始不会初始化  用了这个实例才会加载
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)  //多例
public class HelloWorld {    //创建这个类的对象???

    @Autowired
    public HelloWorld() {
        System.out.println("无参的构造方法");
    }

    @Autowired
    public void hello() {
        System.out.println("hello world");
    }
}
