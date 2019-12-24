package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 被动使用类字段演示二：
 * 通过数组定义来引用类， 不会触发此类的初始化
 */
public class Test13ClassNotInit02 {
    public static void main(String[] args) {
        SuperClass12[] sca = new SuperClass12[10];
        /*
        没有处罚SuperClass的初始化阶段
         */
    }
}
