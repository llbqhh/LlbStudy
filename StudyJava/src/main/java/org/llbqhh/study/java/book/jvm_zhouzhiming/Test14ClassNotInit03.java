package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 被动使用类字段演示三：
 * 常量在编译阶段会存入调用类的常量池中， 本质上并没有直接引用到定义常量的类， 因此不会触发定义常量的类的初始化。
 * vm args：-XX:+TraceClassLoading
 */
public class Test14ClassNotInit03 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO);
        /*
        不会初始化ConstClass，如果加上-XX:+TraceClassLoading参数，则可以看到，甚至都没有加载ConstClass类
        运行结果：
        hello
         */
    }
}
class ConstClass {
    static {
        System.out.println("const init");
    }
    public static final String HELLO = "hello";
}