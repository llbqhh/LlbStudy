package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 暴力停止线程（反面示例）
 */
public class Test06Stop {
    public static void main(String[] args) {
        StopTest t = new StopTest();
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
        t.print();
        /*
        stop直接停止进程（本质上是抛出了ThreadDeath异常，其实是个Error）
        stop方法已经废弃，因为强制让线程停止可能使清理工作得不到完成，另外一个情况就是对锁定的对象进行了解锁，出现数据不一致的情况。
        可以观察到,在catch语句中我们重新把这个ThreadDeath抛出了，但是程序不会显示地捕捉这个异常
        另外，在调用stop后，线程并没有执行完print方法而直接释放了锁，这其实是因为抛出了异常后释放了锁，所以主线程的最后可以成功调用print方法
        运行结果：
        获取锁
        i=1
        stop执行后捕获ThreadDeath异常！
        获取锁
        i=2
        释放锁
         */
    }
}

class StopTest extends Thread {
    private int i = 0;
    @Override
    public void run() {
        try {
            while (true) {
                print();
            }
        } catch (ThreadDeath e) {
            System.out.println("stop执行后捕获ThreadDeath异常！");
            throw e;
        }
    }
    public synchronized void print() {
        System.out.println("获取锁");
        i++;
        System.out.println("i=" + i);
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("释放锁");
    }
}