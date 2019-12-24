package org.llbqhh.study.strategy.singleton;

/**
 * 静态内部类，jvm保证单例
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 *
 * @author lilibiao
 * @date 2019-12-24 13:20
 */
public class Singleton03 {
    private Singleton03() {
    }

    private static class Singleton03Holder {
        private final static Singleton03 INSTANCE = new Singleton03();
    }

    public static Singleton03 getInstance() {
        return Singleton03Holder.INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        Singleton03.getInstance().m();
    }
}
