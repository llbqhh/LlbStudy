package org.llbqhh.test.lock;

public class Loc {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new LockPrint().start();
        }
    }
}
