package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 此代码演示了两点：
 * 1.对象可以在被GC时自我拯救。
 * 2.这种自救的机会只有一次， 因为一个对象的finalize（ ） 方法最多只会被系统自动调用一次
 */
public class Test07FinalizeEscapeGC {
    public static Test07FinalizeEscapeGC saveHook = null;
    public void isAlive() {
        System.out.println("yes, i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize...");
        // 将自己赋给全局变量，避免被垃圾回收
        Test07FinalizeEscapeGC.saveHook = this;
    }

    public static void trySave() throws InterruptedException {
        // 将Test07FinalizeEscapeGC对象的引用去掉并通知垃圾回收
        saveHook = null;
        System.gc();
        Thread.sleep(500);
        if (saveHook != null) {
            saveHook.isAlive();
        } else {
            System.out.println("no, i am dead");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        saveHook = new Test07FinalizeEscapeGC();

        // 第一次将Test07FinalizeEscapeGC对象的引用去掉并通知垃圾回收
        // 对象的finalize方法执行，成功将对象"救活"
        trySave();

        // 第二次将Test07FinalizeEscapeGC对象的引用去掉并通知垃圾回收
        // 对象的finalize方法已经执行过一次，这次不会再执行，对象后续将真正被回收
        trySave();

        /*
        运行结果：
        finalize...
        yes, i am still alive
        no, i am dead
         */
    }
}
