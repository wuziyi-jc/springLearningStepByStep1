package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect   // 切面类：你要增强的功能
@Component   //IOC注解：以实现让spring托管的功能
@Order(value = 100)      //优先级高的先进后出  深度优先的递归
public class LogAspect {

    //切入点的声明   pointcut signature
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))") // the pointcut expression 切入点表达式：那些方法上加增强
    private void add() {

    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))") // the pointcut expression 切入点表达式：那些方法上加增强
    private void update() {

    }

    @Pointcut("add() || update()")
    private void addAndUpdate() {}


    //@After("com.yc.aspect.LogAspect.addAndUpdate()")
    private void bye(JoinPoint jp){//spring是一个ioc容器，它可以使用di将程序运行的信息注入   joinpoint
        System.out.println("===========bye=========");
        //连接点中所有的信息
        Object target=jp.getTarget();
        System.out.println("目标类:"+target);
        System.out.println("方法："+jp.getSignature());
        Object [] objs=jp.getArgs();
        if(objs!=null){
            for(Object o:objs){
                System.out.println(o);
            }
        }
    }


    @Around("execution(* com.yc.biz.StudentBizImpl.find*(..))")
    @Order(value = 1)
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {  //DI  环绕增强规定了第一个参数必须是ProceedingJoinPoint
        System.out.println("=======compute=====进到。。。增强了");
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();//目标类的目标方法
        long end=System.currentTimeMillis();
        System.out.println("compute1要退出。。增强了");
        System.out.println("这个方法运行的时间为："+(end-start));
        return retVal;
    }




    //切入点表达式的语法:
    //modifiers-pattern：修饰符
    //ret-type-pattern：返回类型
    // declaring-type-pattern：
    //name-pattern(param-pattern)：名字模型
    // throws-pattern
    //execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
    //        throws-pattern?)



    //增加的声明
    //@Before("com.yc.aspect.LogAspect.addAndUpdate()")
    public void Log(){
        System.out.println("==========前置增强的日志=========");
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(d);
        System.out.println("执行的时间为："+str);
        System.out.println("===========前置增强的日志结束======");
    }
}
