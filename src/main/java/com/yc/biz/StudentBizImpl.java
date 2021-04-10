package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @program: testspring
 * @description:
 * @author:
 * @create: 2021-04-hg04 14:25
 */
public class StudentBizImpl {
    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao){
        this.studentDao=studentDao;
    }

    public StudentBizImpl(){

    }
    @Autowired
    @Qualifier("studentDaoJpaImpl")
    public void setStudentDao(StudentDao studentDao){this.studentDao=studentDao;}
    public int add(String name){
        System.out.println("===业务层===");
        System.out.println("用户名是否重启");
        int result=studentDao.add(name);
        System.out.println("====业务操作结束");
        return result;
    }
    public void update(String name){
        System.out.println("===业务层===");
        System.out.println("用户名是否重启");
        studentDao.update(name);
        System.out.println("====业务操作结束");
    }


}
