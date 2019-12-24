package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 某些情况下join后面的代码提前运行的解释
 */
public class Test37Join03 {
    public static void main(String[] args) throws InterruptedException {
        ThreadB threadB = new ThreadB();
        ThreadA threadA = new ThreadA(threadB);
        threadA.start();
        threadB.start();
        threadB.join(2000);
        System.out.println("main end " + System.currentTimeMillis());
        /*
        由于a、b线程都会睡眠5秒，所以join方法会很快执行，而join实际上调用的是wait，所以在执行到这里的时候是释放了锁并阻塞
        第一种运行结果是线程a先执行，在线程a执行完毕后，主线程获得了锁，即join获得了锁，然后发现join（2000）已经超时，释放锁后打印了main end，此时b线程获得锁并执行打印
        第二种运行结果是线程a先执行，执行完毕后，b线程获得了锁，然后打印了b相关的输出，最后join方法即主线程才获得了锁，然后打印了mian end
        a线程
        第一种运行结果：
        begin a ThreadName=Thread-1 1571904107378
          end a ThreadName=Thread-1 1571904112380
        main end 1571904112380
        begin b ThreadName=Thread-0 1571904112380
          end b ThreadName=Thread-0 1571904117385

        第二种运行结果：
        begin a ThreadName=Thread-1 1571904200905
          end a ThreadName=Thread-1 1571904205906
        begin b ThreadName=Thread-0 1571904205906
          end b ThreadName=Thread-0 1571904210911
        main end 1571904210911
         */
    }
}
class ThreadA extends Thread {
     private ThreadB threadB;
     public ThreadA(ThreadB threadB) {
         this.threadB = threadB;
     }

    @Override
    public void run() {
        try {
            synchronized (threadB) {
                System.out.println("begin a ThreadName=" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println("  end a ThreadName=" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class ThreadB extends Thread {
    @Override
    public synchronized void run() {
        try {
            System.out.println("begin b ThreadName=" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("  end b ThreadName=" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}