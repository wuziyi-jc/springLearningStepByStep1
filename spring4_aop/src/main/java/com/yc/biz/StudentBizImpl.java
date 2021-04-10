package com.yc.biz;


import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class StudentBizImpl implements StudentBiz{

    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentBizImpl() {

    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    //@Inject  //javax中的依赖注入,如果有多个对象.(比如这里有 StudentDaoJpaImpl和StudentDaoMybatisImpl对象)
    //因为有多个对象可以注入,所以这里必须用@Named("studentDaoJpaImpl"),如只有一个对象,则不需要写
    //@MyAutowired   //org.springframework
    //@MyQualifier("studentDaoJpaImpl")   //如果有多个对象,(比如这里有 StudentDaoJpaImpl和StudentDaoMybatisImpl对象)
    //因为有多个对象可以注入,所以这里必须用@Named("studentDaoJpaImpl"),如只有一个对象,则不需要写
    //@MyResource(name = "studentDaoJpaImpl")
    @Autowired
    @Qualifier("studentDaoJpaImpl")
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


    @Override
    public int add(String name) {
        System.out.println("========业务层=========");
        System.out.println("用户名是否重名");
        int result = studentDao.add(name);
        System.out.println("======业务操作结束======");
        return result;
    }


    @Override
    public void update(String name) {
        System.out.println("========业务层=========");
        System.out.println("用户名是否重名");
        studentDao.update(name);
        System.out.println("======业务操作结束======");
    }


    @Override
    public void find(String name) {
        System.out.println("========业务层=========");
        System.out.println("业务层查找用户名：" + name);
        studentDao.find(name);
        Random r = new Random();
        try {
            Thread.sleep( r.nextInt(10) );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("======业务操作结束======");
    }
}
