package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 一生产与一消费：操作值
 */
public class Test27ProducerConsumer01 {
    public static void main(String[] args) {
        String lock = "lock";
        Producer2701 p = new Producer2701(lock);
        Consumer2701 c = new Consumer2701(lock);
        p.start();
        c.start();
        /*
        程序会一直生产并消费数据
        运行结果：
        set的值是1571649793640_27010753569137
        get的值是1571649793640_27010753569137
        set的值是1571649793640_27010753614217
        get的值是1571649793640_27010753614217
        set的值是1571649793640_27010753639878
        get的值是1571649793640_27010753639878
        set的值是1571649793640_27010753670897
         */
    }
}

class ValueObject {
    public static String value = "";
}

class Producer2701 extends Thread{
    String lock;
    public Producer2701(String lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            this.setValue();
        }
    }

    public void setValue() {
        try {
            synchronized (lock) {
                if (!ValueObject.value.equals("")) {
                    lock.wait();
                }
                String value = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("set的值是" + value);
                ValueObject.value = value;
                lock.notify();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer2701 extends Thread {
    private String lock;
    public Consumer2701(String lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            this.getValue();
        }
    }

    public void getValue() {
        try {
            synchronized (lock) {
                if (ValueObject.value.equals("")) {
                    lock.wait();
                }
                System.out.println("get的值是" + ValueObject.value);
                ValueObject.value = "";
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}