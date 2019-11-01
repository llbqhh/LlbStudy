package org.llbqhh.study.java.book.jvm_zhouzhiming;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区内存溢出
 * jdk1.6 VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * jdk1.6 VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class Test05OOMMethodArea {
    public static void main(String[] args) {
        while (true) {
            // enhancer类可以动态为指定的类生成代理子类并代理父类的方法
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Test05OOMMethodArea.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o, args);
                }
            });
            enhancer.create();
        }
        /*
        我是在jdk1.8测试的，很快会抛出oom异常，1.8的方法区在Metaspace区域，实际是Native memory
        运行结果：
        Caused by: java.lang.OutOfMemoryError: Metaspace
            at java.lang.ClassLoader.defineClass1(Native Method)
            at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
            ... 11 more
         */
    }
}
