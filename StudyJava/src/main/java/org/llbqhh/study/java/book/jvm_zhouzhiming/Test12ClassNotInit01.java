package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 被动使用类字段演示一：
 * 通过子类引用父类的静态字段， 不会导致子类初始化
 * vm args：-XX:+TraceClassLoading
 */
public class Test12ClassNotInit01 {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
        /*
        对于静态字段， 只有直接定义这个字段的类才会被初始化， 因此通过其子类来引用父类中定义的静
        态字段， 只会触发父类的初始化而不会触发子类的初始化。 至于是否要触发子类的加载和验
        证， 在虚拟机规范中并未明确规定， 这点取决于虚拟机的具体实现。 对于Sun HotSpot虚拟机
        来说， 可通过-XX:+TraceClassLoading参数观察类加载的情况。从结果来看子类是在父类后加载了。
        运行结果：
        [Loaded org.llbqhh.study.java.book.jvm_zhouzhiming.SuperClass12 from file:/Users/lilibiao/IdeaProjects/LlbStudy/StudyJava/target/classes/]
        [Loaded org.llbqhh.study.java.book.jvm_zhouzhiming.SubClass from file:/Users/lilibiao/IdeaProjects/LlbStudy/StudyJava/target/classes/]
        super init...
        1
         */
    }
}

class SuperClass12 {
    static {
        System.out.println("super init...");
    }
    public static int value = 1;
}

class SubClass extends SuperClass12 {
    static {
        System.out.println("sub init...");
    }
}