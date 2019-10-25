package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * initValue方法解决ThreadLock初次调用get方法返回null的问题
 */
public class Test39ThreadLocalInitValue {
    public static ThreadLocal threadLocal = new ThreadLocal();
    public static ThreadLocalExt39 threadLocalExt39 = new ThreadLocalExt39();
    public static void main(String[] args) {
        System.out.println(threadLocal.get());
        System.out.println(threadLocalExt39.get());
        threadLocalExt39.set("my value");
        System.out.println(threadLocalExt39.get());
        /*
        ThreadLocal默认的初始值为null，如果某些情况需要初始化初始值，可以重写initialValue方法生成初始值
        运行结果：
        null
        默认值！
        my value
         */
    }
}
class ThreadLocalExt39 extends ThreadLocal {
    @Override
    protected Object initialValue() {
        return "默认值！";
    }
}