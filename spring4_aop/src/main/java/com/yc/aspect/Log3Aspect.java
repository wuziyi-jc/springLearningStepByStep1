package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 1)
public class Log3Aspect {

    @Around("execution(* com.yc.biz.StudentBizImpl.find*(..))")
    public Object compute2(ProceedingJoinPoint pjp) throws Throwable {  //DI  环绕增强规定了第一个参数必须是ProceedingJoinPoint
        System.out.println("*********cmpute2=====进到。。。增强了");
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();//目标类的目标方法
        long end=System.currentTimeMillis();
        System.out.println("compute2要退出。。增强了");
        System.out.println("这个方法运行的时间为："+(end-start));
        return retVal;
    }

}
