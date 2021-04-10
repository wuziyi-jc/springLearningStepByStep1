package com.yc;


import com.yc.biz.StudentBizImpl;
import com.yc.springframework.context.MyAnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext ac = new MyAnnotationConfigApplicationContext(MyAppConfig.class);
        StudentBizImpl hw = (StudentBizImpl) ac.getBean("studentBizImpl");
        hw.add("abc");
    }
}
