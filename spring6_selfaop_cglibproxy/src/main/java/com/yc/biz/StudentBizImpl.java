package com.yc.biz;

public class StudentBizImpl {

    public int add(String name){
        System.out.println("调用了studentBizImpl中的 add"+name);
        return 100;
    }

    public void update(String name) {

        System.out.println("调用了studentBizImpl中的 update"+name);
    }


    public String find(String name) {
        System.out.println("调用了studentBizImpl中的 find"+name);
        return name+"=======";
    }
}
