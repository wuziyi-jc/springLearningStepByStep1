package com.yc;

import com.yc.biz.StudentBizImpl;

public class Test {
    public static void main(String[] args) {
        StudentBizImpl sbi =new StudentBizImpl();

        LogAspectCglib lc=new LogAspectCglib(sbi);

        //生成代理
        Object obj=lc.createProxy();
        System.out.println(obj);   //obj.toString()
        if (obj instanceof StudentBizImpl){
            StudentBizImpl s= (StudentBizImpl) obj;

            s.find("张三");
            s.add("王五");
            s.update("李四");
        }
    }
}
