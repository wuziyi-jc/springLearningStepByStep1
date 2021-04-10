package com.yc.dao;


import java.util.Random;

//@MyRepository   //异常转化 从Exception转为RuntimeException
public class StudentDaoMybatisImpl implements StudentDao {
    @Override
    public int add(String name) {
        System.out.println("mybatis添加学生:" + name);
        Random r = new Random();
        return r.nextInt();
    }

    @Override
    public void update(String name) {
        System.out.println("mybatis更新学生:" + name);
    }

    @Override
    public void find(String name) {
        System.out.println("mybatis查找学生:" + name);
    }
}
