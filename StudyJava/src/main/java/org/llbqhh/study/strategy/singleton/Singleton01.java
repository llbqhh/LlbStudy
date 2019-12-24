package org.llbqhh.study.strategy.singleton;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单例，jvm保证线程安全
 * 唯一缺点，不管是否用到，都会在类加载时完成实例化
 * 例如使用Class.forName("")可以加载类
 * @author lilibiao
 * @date 2019-12-24 13:05
 */
public class Singleton01 {
    private static final Singleton01 INSTANCE = new Singleton01();
    private Singleton01() {};
    public static Singleton01 getInstance() {
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        Singleton01.getInstance().m();
        Singleton01 s1 = Singleton01.getInstance();
        Singleton01 s2 = Singleton01.getInstance();
        System.out.println(s1 == s2);
    }
}
