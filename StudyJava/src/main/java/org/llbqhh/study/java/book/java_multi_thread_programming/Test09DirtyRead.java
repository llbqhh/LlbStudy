package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 脏读
 */
public class Test09DirtyRead {
    public static void main(String[] args) throws InterruptedException {
        DirtyReadObject dirtyReadObject = new DirtyReadObject();
        DirtyReadThread dirtyReadThread = new DirtyReadThread(dirtyReadObject);
        dirtyReadThread.start();
        Thread.sleep(1000);
        dirtyReadObject.getValue();

        /*
        由于setValue方法在修改了username后即休眠，主线程在调用getValue时username已经修改为B，而password还未修改，造成脏读
        运行结果：
        get method thread name=main username=B password=AA
        set method thread name=Thread-0 username=B password=BB

        解决办法是在getValue方法上加synchronized关键字，使其成为同步方法，这样就会等setValue释放锁后此方法才能执行
        getValue方法加锁后的运行结果：
        set method thread name=Thread-0 username=B password=BB
        get method thread name=main username=B password=BB
         */
    }
}

class DirtyReadThread extends Thread {
    private DirtyReadObject dirtyReadObject;
    public DirtyReadThread(DirtyReadObject dirtyReadObject) {
        this.dirtyReadObject = dirtyReadObject;
    }

    @Override
    public void run() {
        super.run();
        dirtyReadObject.setValue("B", "BB");
    }
}

class DirtyReadObject {
    public String username = "A";
    public String password = "AA";
    public synchronized void setValue(String username, String password) {
        try {
            this.username = username;
            Thread.sleep(5000);
            this.password = password;
            System.out.println(String.format("set method thread name=%s username=%s password=%s", Thread.currentThread().getName(), username, password));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getValue() {
//    synchronized public void getValue() { //加锁同步后不会出现脏读
        System.out.println(String.format("get method thread name=%s username=%s password=%s", Thread.currentThread().getName(), username, password));
    }
}