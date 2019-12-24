package org.llbqhh.study.strategy.singleton;

/**
 * 双重检查锁double check
 *
 * @author lilibiao
 * @date 2019-12-24 13:10
 */
public class Singleton02 {
    // 加volatile是为了禁止指令重排序带来的问题
    private static volatile Singleton02 INSTANCE;

    private Singleton02() {
    }

    public static Singleton02 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton02.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton02();
                }
            }
        }
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
//        Singleton02.getInstance().m();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Singleton02.getInstance().hashCode())).start();
        }
    }
}
