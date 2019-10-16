package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 简单演示内部类及静态内部类
 */
public class Test16InnerClass {
    public static void main(String[] args) {
        //
        InnerClassTest innerClassTest = new InnerClassTest();
        // 创建内部类需要外部类的对象存在
        InnerClassTest.MyInnerClass myInnerClass = innerClassTest.new MyInnerClass();
        // 创建静态内部类不需要外部对象存在，可直接创建
        InnerClassTest.MyStaticInnerClass myStaticInnerClass = new InnerClassTest.MyStaticInnerClass();

        /*
        运行结果：
        inner...
        static inner...
         */
    }
}

class InnerClassTest {
    class MyInnerClass {
        MyInnerClass() {
            System.out.println("inner...");
        }
    }

    static class MyStaticInnerClass {
        MyStaticInnerClass() {
            System.out.println("static inner...");
        }
    }
}
