package com.yc;

import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;


@MyConfiguration    //表示当前的类是一个配置类
@MyComponentScan(basePackages = {"com.yc.dao", "com.yc.biz"}) //将来要托管的bean要扫描的包及子包
public class MyAppConfig {  //java容器的配置

//    @MyBean
//    public HelloWorld hw() {   //method.invoke( MyAppConfig对象 , xxx)
//        return new HelloWorld();
//    }
//
//    @MyBean
//    public HelloWorld hw2() {   //method.invoke( MyAppConfig对象 , xxx)
//        return new HelloWorld();
//    }

}
