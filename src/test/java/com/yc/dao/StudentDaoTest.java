package com.yc.dao;

import com.yc.AppConfig;
import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentDaoTest extends TestCase {

    //    private StudentDao studentDao;
//    private StudentBizImpl studentBizImpl;
    private ApplicationContext ac;
    private StudentBizImpl studentBiz;
    @Override
    public void setUp() throws Exception {
        //1.能否自动完成   实例化对象   -> IOC 控制 反转->由容器实例化对象，由容器来完成
//        studentDao = new StudentDaoJpaImpl();
//
//        // studentBizImpl = new StudentBizImpl( studentDao );  //IOC
//        studentBizImpl = new StudentBizImpl();
//        //2.能否自动化完成  装配过程    -> DI 依赖注入-> 由容器装配对象
//        studentBizImpl.setStudentDao(studentDao);
        ac = new AnnotationConfigApplicationContext(AppConfig.class);

        studentBiz= (StudentBizImpl) ac.getBean("studentBizImpl");

    }


    public void testAdd() {
        //studentDao.add("张三");
        studentBiz.add("张三");


    }

    public void testUpdate() {
        //studentDao.update("张三");
        studentBiz.update("张三");
    }

//    public void testBizAdd() {
//
//        //studentBizImpl.add("张三");
//    }
}