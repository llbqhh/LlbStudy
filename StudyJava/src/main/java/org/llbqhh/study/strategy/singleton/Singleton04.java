package org.llbqhh.study.strategy.singleton;

/**
 * 枚举方式实现，可以防止反序列化，
 * 因为枚举类没有构造方法，反射也无法处理
 *
 * @author lilibiao
 * @date 2019-12-24 13:23
 */
public enum Singleton04 {
    INSTANCE;

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        Singleton04.INSTANCE.m();
    }
}
