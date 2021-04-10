package com.yc.biz;

import com.yc.MyAppConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyAppConfig.class)
public class StudentBizImplTest {

    @Autowired     //按类型注入  ，如有多个同类托管bean，则报错，  要不就要加入@Qualifer来约定注入的beanid
    //@Resource    //先按名字注入， 如不行再按类型注入，此时，
    private StudentBiz sbi;

    @Test
    public void testAdd() {
        sbi.add("张三");
    }
    @Test
    public void testUpdate() {
        sbi.update("张三");
    }
    @Test
    public void testFind() {
        sbi.find("张三");
    }
}