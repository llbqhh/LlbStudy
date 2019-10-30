package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 线程对象异常处理
 */
public class Test48ThreadException {
    public static void main(String[] args) {
        // 使用setDefaultUncaughtExceptionHandler来设置线程类的默认的异常处理器
        MyThread48.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程：" + t.getName() + "出现了异常，被类级别的UncaughtExceptionHandler处理。");
//                e.printStackTrace();
            }
        });

        MyThread48 myThread4801 = new MyThread48();
        // 使用setUncaughtExceptionHandler方法来设置某个具体线程对象的异常处理器。
        myThread4801.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程：" + t.getName() + "出现了异常，被对象级别的UncaughtExceptionHandler处理。");
//                e.printStackTrace();
            }
        });

        MyThread48 myThread4802 = new MyThread48();

        myThread4801.setName("myThread4801");
        myThread4802.setName("myThread4802");

        myThread4801.start();
        myThread4802.start();

        /*
        从运行结果可以看出，
        myThread4801的异常由线程对象调用setUncaughtExceptionHandler方法设置的UncaughtExceptionHandler处理了，
        myThread4802的异常由MyThread48类调用的setDefaultUncaughtExceptionHandler方法设置的UncaughtExceptionHandler处理了。

        运行结果：
        线程：myThread4801出现了异常，被对象级别的UncaughtExceptionHandler处理。
        线程：myThread4802出现了异常，被类级别的UncaughtExceptionHandler处理。
        */
    }
}
class MyThread48 extends Thread {
    @Override
    public void run() {
        String username = null;
        // 这一句一定会抛空指针异常
        System.out.println(username.hashCode());
    }
}
