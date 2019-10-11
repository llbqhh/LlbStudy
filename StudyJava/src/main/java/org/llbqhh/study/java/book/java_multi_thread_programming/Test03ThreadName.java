package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 线程名称相关，当前线程和this的区别
 */
public class Test03ThreadName {
    public static void main(String[] args) {
        TestThreadName testThreadName = new TestThreadName();
        Thread t = new Thread(testThreadName);
        t.setName("a");
        testThreadName.setName("b");
        t.start();
        /*
        Thread.currentThread是当前执行线程，而this指的是运行方法的对象
        在构造方法中，由于是主线程调用TestThreadName的构造方法，所以Thread.currentThread()的线程名为main，线程id为1
        而this代表的testThreadName对象，在创建时已经自动分配了新的name和id，分别为Thread-0和11
        对象t是程序又新创建的一个对象，虽然它将testThreadName传入，但是t本身也是一个新的Thread对象，所以也重新分配了name和id，分别为Thread-1和11
        而我们将t对象的name重新set成为"a"，将testThreadName对象的name重新set成为"b"
        在t线程启动后，实际上当前线程是t，但是运行的方法是在testThreadName对象中，所以出现Thread.currentThread的name为a（即t的name），this.getName()为b（即testThreadName的name）
        运行结果：
        TestThreadName--begin
        Thread.currentThread().getName()=main
        Thread.currentThread().getId()=1
        this.getName()=Thread-0
        this.getId()=11
        TestThreadName--end
        run--begin
        Thread.currentThread().getName()=a
        Thread.currentThread().getId()=12
        this.getName()=b
        this.getId()=11
        run--end
         */
    }
}

class TestThreadName extends Thread {
    public TestThreadName() {
        System.out.println("TestThreadName--begin");
        System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
        System.out.println("Thread.currentThread().getId()=" + Thread.currentThread().getId());
        System.out.println("this.getName()=" + this.getName());
        System.out.println("this.getId()=" + this.getId());
        System.out.println("TestThreadName--end");
    }

    @Override
    public void run() {
        System.out.println("run--begin");
        System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
        System.out.println("Thread.currentThread().getId()=" + Thread.currentThread().getId());
        System.out.println("this.getName()=" + this.getName());
        System.out.println("this.getId()=" + this.getId());
        System.out.println("run--end");
    }
}