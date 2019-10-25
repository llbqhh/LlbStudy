package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 通过管道进行线程间通信：字节流
 */
public class Test31PipeStream {
    public static void main(String[] args) throws IOException, InterruptedException {
        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();

        //谁connect谁都可以
//        inputStream.connect(outputStream);
        outputStream.connect(inputStream);

        WriteDataThread3101 write = new WriteDataThread3101(outputStream);
        ReadDataThread3101 read = new ReadDataThread3101(inputStream);

        read.start();
        Thread.sleep(2000);
        write.start();

        /*
        运行结果
        read :
        write :
        123456789101112131415161718192021222324252627282930
        123456789101112131415161718192021222324252627282930
         */
    }
}

class WriteDataThread3101 extends Thread {
    private PipedOutputStream out;
    public WriteDataThread3101(PipedOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        this.writeMethod(out);
    }

    public void writeMethod(PipedOutputStream out) {
        try {
            System.out.println("write :");
            for (int i = 0; i < 30; i++) {
                String outData = "" + (i + 1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ReadDataThread3101 extends Thread {
    private PipedInputStream input;
    public ReadDataThread3101(PipedInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        this.readMethod(input);
    }

    public void readMethod(PipedInputStream input) {
        try {
            System.out.println("read :");
            byte[] byteArray = new byte[20];
            int readLength = input.read(byteArray);
            while (readLength != -1) {
                String newData = new String(byteArray, 0, readLength);
                System.out.print(newData);
                readLength = input.read(byteArray);
            }
            System.out.println();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}