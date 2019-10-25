package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 子线程在继承父线程值的同时，可以修改这个值
 */
public class Test41InheritableThreadLocal02 {
    public static void main(String[] args) {
        // 这次使用默认初始值
        System.out.println("main inheritableThreadLocalExt:" + Tools41.inheritableThreadLocalExt.get());
        Thread child = new Thread() {
            @Override
            public void run() {
                System.out.println("child inheritableThreadLocalExt:" + Tools41.inheritableThreadLocalExt.get());
            }
        };
        child.start();
        /*
        从运行结果可以看到inheritableThreadLocalExt中的值，子线程可以继承到，并且子线程可以在继承值的基础上，使用childValue方法灵活定义自己的值
        运行结果：
        main inheritableThreadLocalExt:main value 1571908894821
        child inheritableThreadLocalExt:main value 1571908894821 child value!
         */
    }
}

class Tools41 {
    public static InheritableThreadLocalExt41 inheritableThreadLocalExt = new InheritableThreadLocalExt41();
}
class InheritableThreadLocalExt41 extends InheritableThreadLocal {
    @Override
    protected Object initialValue() {
        return "main value " + System.currentTimeMillis();
    }

    @Override
    protected Object childValue(Object parentValue) {
        return parentValue + " child value!";
    }
}