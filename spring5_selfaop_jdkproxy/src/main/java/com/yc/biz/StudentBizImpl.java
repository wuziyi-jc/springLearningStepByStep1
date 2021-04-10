package com.yc.biz;

public class StudentBizImpl implements StudentBiz {
    @Override
    public int add(String name) {
        System.out.println("调用了studentBizImpl中的 add"+name);
        return 100;
    }

    @Override
    public void update(String name) {

        System.out.println("调用了studentBizImpl中的 update"+name);
    }

    @Override
    public String find(String name) {
        System.out.println("调用了studentBizImpl中的 find"+name);
        return name+"===========";
    }
}
