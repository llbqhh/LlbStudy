package org.llbqhh.test.jvm;

public class GcTest {
    public static void main(String[] args) throws InterruptedException {
        GcTest2 g = new GcTest2();
        g.test();
        g = null;
        System.out.println("ok");
        while (true) {
            Thread.sleep(1000);
            Runtime.getRuntime().gc();
        }
    }
}
