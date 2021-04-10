package com.yc.springframework.context;


import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class MyAnnotationConfigApplicationContext implements MyApplicationContext {

    private Map<String,Object> beanMap = new HashMap<String, Object>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses) {
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        if( componentClasses == null || componentClasses.length <= 0) {
            throw new RuntimeException("没有指定配置类");
        }
        for(Class cl : componentClasses) {
            //请实现这个里面的代码
            //原码1.  只实现IOC, MyPostConstruct  MyPreDestroy
            if(!cl.isAnnotationPresent(MyConfiguration.class)) {
                continue;
            }
            String [] basePackages = getAppConfigBasePackages( cl );
            if( cl.isAnnotationPresent(MyComponentScan.class)) {
                MyComponentScan mcs = (MyComponentScan) cl.getAnnotation( MyComponentScan.class );
                if( mcs.basePackages()!=null && mcs.basePackages().length > 0) {
                    basePackages = mcs.basePackages();
                }
            }
            //处理@MyBean的情况
            Object obj = cl.newInstance(); //obj就是当前解析的MyAppConfig对象
            hardleAtMyBean(cl, obj);
            //处理   basePackages 基本包下的所有的托管bean
            for( String basePackage : basePackages) {
                scanPackageAndSubPackageClasses(basePackage);   //扫描所有的包 ，及子包中的MyBean
            }
            //继续其他托管bean
            hardleAtMyBean();
            //版本2：实现DI   循环beanMap中的每个bean  找到它们每个类中的每个由@Autowired @Resource注解的方法以实现di
            handleDi( beanMap );

        }


    }

    private void handleDi(Map<String, Object> beanMap) throws InvocationTargetException, IllegalAccessException {
        Collection objecCollection = beanMap.values();
        for( Object obj : objecCollection) {
            Class cls = obj.getClass();
            Method [] ms = cls.getDeclaredMethods();
            for(Method m : ms) {
                if( m.isAnnotationPresent(MyAutowired.class) && m.getName().startsWith("set")) {
                    invokeAutowiredMethod(m,obj);
                }else if(m.isAnnotationPresent(MyResource.class) && m.getName().startsWith("set")) {
                    invokeResourceMethod(m,obj);
                }
            }
            Field[] fs = cls.getDeclaredFields();
            for(Field field : fs) {
                if( field.isAnnotationPresent(MyAutowired.class)) {

                }else if(field.isAnnotationPresent(MyResource.class)) {

                }
            }
        }
    }
    private void invokeResourceMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.取出  MyResource中的name属性值
        MyResource mr = m.getAnnotation(MyResource.class);
        String beanId = mr.name();
        //2.如果没有 则取出  m方法中参数的类型名，改成首字小写  当成beanId
        if(beanId == null || beanId.equalsIgnoreCase("")) {
            String pname = m.getParameterTypes()[0].getSimpleName();
            beanId = pname.substring(0, 1).toLowerCase() + pname.substring(1);
        }
        //3.从beanMap取出
        Object o = beanMap.get(beanId);
        //4.invoke
        m.invoke(obj, o);
    }

    private void invokeAutowiredMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1. 取出 m的参数的类型
        // StudentDao接口类型
        Class typeClass = m.getParameterTypes()[0];
        //2. 从beanMap中循环所有的object
        Set<String> keys = beanMap.keySet();
        for( String key : keys) {
            //4. 如果是 则从beanMap取出
            Object o = beanMap.get(key);
            //3. 判断 这些object是否为 参数类型的实例 instanceof
            Class [] interfaces = o.getClass().getInterfaces();
            for(Class c : interfaces){
                System.out.println(c.getName() + "\t" + typeClass);
                if(c == typeClass) {
                    //5. invoke
                    m.invoke(obj, o);
                    break;
                }
            }
        }
        //m.invoke(   obj,   );
    }


    /*
    处理managedBeanClasses 所有的Class类，筛选出所有的@Component @Service @Repository的类，存到beanMap中
     */
    private void hardleAtMyBean() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for( Class c : managedBeanClasses ) {
            if( c.isAnnotationPresent(MyComponent.class) ) {
               saveManagedBean(c);
            }else if( c.isAnnotationPresent(MyService.class)) {
                saveManagedBean(c);
            }else if( c.isAnnotationPresent(MyRepository.class)) {
                saveManagedBean(c);
            }else if( c.isAnnotationPresent(MyController.class)) {
                saveManagedBean(c);
            }else {
                continue;
            }
        }
    }

    private void saveManagedBean( Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = c.newInstance();
        handlePostConstruct(o, c);
        String beanId = c.getSimpleName().substring(0,1).toLowerCase() + c.getSimpleName().substring(1);
        beanMap.put( beanId, o );
    }

    /*
    扫描包和子包
     */
    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath = basePackage.replaceAll("\\.","/"); //com/yc/bean
        System.out.println("扫描包路径:" + basePackage + "替换后:" + packagePath);
        Enumeration<URL> files = Thread.currentThread().getContextClassLoader().getResources( packagePath );
        while(files.hasMoreElements()) {
            URL url = files.nextElement();
            System.out.println("配置的扫描路径为:" + url.getFile());//E:/ideaProjects/testspring/spring3_myspringframework/target/classes/com/yc/bean
            //TODO:递归这些目录，查找  .class文件
            findClassesInPackages(url.getFile(), basePackage);

        }
    }

    private Set<Class> managedBeanClasses = new HashSet<Class>();
    /**
     * 查找 file 下面及子包所有的要托管的class，存到一个Set(managedBeanClasses)中
     * @param file
     * @param basePackage
     */
    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f = new File(file);
        File [] classFiles = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        //System.out.println(classFiles);
        for(File cf : classFiles ) {
            if( cf.isDirectory() ) {
                //如果目录 则递归
                //拼接  子目录
                basePackage +="." + cf.getName().substring(cf.getName().lastIndexOf("/")+1);
                findClassesInPackages(cf.getAbsolutePath(), basePackage);
            }else {
                //加载  cf   作为 class文件
                URL [] urls = new URL[]{};
                URLClassLoader ucl = new URLClassLoader(urls);
                Class c = ucl.loadClass(basePackage+ "." + cf.getName().replace(".class",""));
                managedBeanClasses.add(c);
                //managedBeanClasses.add()
            }
        }
    }

    /*
    处理MyAppConfig配置类中的@Bean注解，完成IOC操作...
     */
    private void hardleAtMyBean(Class cls, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1. 获取cls中所以的method
        Method[] ms = cls.getDeclaredMethods();
        //2.循环 判断 每个method上是否有  @MyBean注解
        for( Method m : ms) {
            if( m.isAnnotationPresent(MyBean.class)) {
                Object o = m.invoke(obj);//3.有，则invoke它，它有返回值，将空上返回值 存到 beanMap 键是方法名，值是返回对象
                //TODO: 加入处理  @MyBean注解对应的方法所实例化的@MyPostConstrut 对应的方法
                handlePostConstruct( o, o.getClass() );  //o在这里指  HelloWorld对象  o.getClass()它的反射对象
                beanMap.put(m.getName(), o);
            }
        }
    }

    /*
    处理一个Bean中的 @MyPostConstruct对应的方法
     */
    private void handlePostConstruct(Object o, Class<?> cls) throws InvocationTargetException, IllegalAccessException {
        Method [] ms = cls.getDeclaredMethods();
        for(Method m : ms) {
            if( m.isAnnotationPresent(MyPostConstruct.class)) {
                m.invoke( o );
            }
        }
    }

    /*
    获取当前  AppConfig类所在的包路径
     */
    private String[] getAppConfigBasePackages( Class cl) {
        String[] paths = new String[1];
        paths[0] = cl.getPackage().getName();
        return paths;
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }

}
