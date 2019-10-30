package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 线程组对象异常处理
 */
public class Test49ThreadGroupException {
    public static void main(String[] args) {
        // 使用我们自己实现的ThreadGroup49，其中指定了当某线程出错时，将组内所有线程interrupt的操作
        ThreadGroup49 group = new ThreadGroup49("my group");

        // 创建几个会一直运行的线程
        MyThread49[] myThread49 = new MyThread49[5];
        for (int i = 0; i < myThread49.length; i++) {
            myThread49[i] = new MyThread49(group, "线程" + i, "1");
            myThread49[i].start();
        }

        // 创建一个会在运行后报错的线程
        MyThread49 newT = new MyThread49(group, "报错线程", "a");
        newT.start();

        /*
        从结果可以看出，在抛出异常后，所有线程都收到了interrupt的消息，并由代码控制退出了循环
        运行结果：
        死循环中：线程4
        java.lang.NumberFormatException: For input string: "a"
        死循环中：线程3
        死循环中：线程3
            at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
        死循环中：线程1
            at java.lang.Integer.parseInt(Integer.java:580)
        死循环中：线程3
            at java.lang.Integer.parseInt(Integer.java:615)
        死循环中：线程3
            at org.llbqhh.study.java.book.java_multi_thread_programming.MyThread49.run(Test49ThreadGroupException.java:44)
        死循环中：线程4
        死循环中：线程0
        死循环中：线程2
        死循环中：线程3
        死循环中：线程1
         */
    }
}
class ThreadGroup49 extends ThreadGroup {
    public ThreadGroup49(String name) {
        super(name);
    }

    /**
     * 重写uncaughtException方法，对整组线程进行interrupt操作
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        // 停止整组对线程
        this.interrupt();
    }
}
class MyThread49 extends Thread {
    private String num;
    public MyThread49(ThreadGroup group, String name, String num) {
        super(group, name);
        this.num = num;
    }

    @Override
    public void run() {
        int numInt = Integer.parseInt(num);
        while (!this.isInterrupted()) {
            System.out.println("死循环中：" + Thread.currentThread().getName());
        }
    }
}