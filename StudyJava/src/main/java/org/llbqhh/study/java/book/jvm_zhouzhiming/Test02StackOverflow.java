package org.llbqhh.study.java.book.jvm_zhouzhiming;
/**
 * 栈StackOverflow简单测试
 * VM Args： -Xss160k
 */
public class Test02StackOverflow {
    private static long stackLength = 1;
    public static void main(String[] args) {
        try{
            stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + stackLength);
            e.printStackTrace();
        }
        /*
        栈深度过大，抛出StackOverflowError异常
        运行结果：
        stack length:849
        java.lang.StackOverflowError
            at org.llbqhh.study.java.book.jvm_zhouzhiming.Test02StackOverflow.stackLeak(Test02StackOverflow.java:18)
            at org.llbqhh.study.java.book.jvm_zhouzhiming.Test02StackOverflow.stackLeak(Test02StackOverflow.java:19)
            at org.llbqhh.study.java.book.jvm_zhouzhiming.Test02StackOverflow.stackLeak(Test02StackOverflow.java:19)
            at org.llbqhh.study.java.book.jvm_zhouzhiming.Test02StackOverflow.stackLeak(Test02StackOverflow.java:19)
            at org.llbqhh.study.java.book.jvm_zhouzhiming.Test02StackOverflow.stackLeak(Test02StackOverflow.java:19)
         */
    }

    public static void stackLeak() {
        stackLength ++;
        stackLeak();
    }
}