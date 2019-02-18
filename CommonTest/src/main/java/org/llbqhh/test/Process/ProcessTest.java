package org.llbqhh.test.Process;

import java.io.IOException;

public class ProcessTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread t = new RsyncThread();
        t.start();
        System.exit(1);
    }
}

class RsyncThread extends Thread {
    @Override
    public void run() {
        Process p;
        try {
            p = Runtime.getRuntime().exec("rsync -azvP --bwlimit=64 --port=31777  ip::");
            p.waitFor();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.run();
    }
}
