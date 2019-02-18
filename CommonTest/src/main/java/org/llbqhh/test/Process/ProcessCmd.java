package org.llbqhh.test.Process;

/**
 * Created by lilibiao on 2017/8/16.
 */
public class ProcessCmd {
    public static void main(String[] args) {
        while (true) {
            System.out.println("I'm process test...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
