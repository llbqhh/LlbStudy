package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 使用InheritableThreadLocal可以让子线程取得从父线程继承下来的值
 */
public class Test40InheritableThreadLocal {
    public static void main(String[] args) {
        // 主线程先给两个threadlocal设置值
        Tools40.inheritableThreadLocal.set("main value");
        Tools40.threadLocal.set("main value");
        System.out.println("main inheritableThreadLocal:" + Tools40.inheritableThreadLocal.get());
        System.out.println("main threadLocal:" + Tools40.threadLocal.get());
        Thread child = new Thread() {
            @Override
            public void run() {
                System.out.println("child inheritableThreadLocal:" + Tools40.inheritableThreadLocal.get());
                System.out.println("child threadLocal:" + Tools40.threadLocal.get());
            }
        };
        child.start();
        /*
        从运行结果可以看到inheritableThreadLocal中的值，子线程可以继承到，而threadLocal的值自线程无法继承
        运行结果：
        main inheritableThreadLocal:main value
        main threadLocal:main value
        child inheritableThreadLocal:main value
        child threadLocal:null
         */
    }
}
class Tools40 {
    public static InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();
    public static ThreadLocal threadLocal = new ThreadLocal();
}