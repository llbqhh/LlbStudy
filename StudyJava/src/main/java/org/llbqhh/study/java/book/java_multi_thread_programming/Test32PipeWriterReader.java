package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 通过管道进行线程间通信：字符流
 */
public class Test32PipeWriterReader {
    public static void main(String[] args) throws IOException, InterruptedException {
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();

        //谁connect谁都可以
//        writer.connect(reader);
        reader.connect(writer);

        WriteDataThread3102 write = new WriteDataThread3102(writer);
        ReadDataThread3102 read = new ReadDataThread3102(reader);

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

class WriteDataThread3102 extends Thread {
    private PipedWriter writer;
    public WriteDataThread3102(PipedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run() {
        this.writeMethod(writer);
    }

    public void writeMethod(PipedWriter writer) {
        try {
            System.out.println("write :");
            for (int i = 0; i < 30; i++) {
                String outData = "" + (i + 1);
                writer.write(outData);
                System.out.print(outData);
            }
            System.out.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadDataThread3102 extends Thread {
    private PipedReader reader;
    public ReadDataThread3102(PipedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        this.readMethod(reader);
    }

    public void readMethod(PipedReader reader) {
        try {
            System.out.println("read :");
            char[] charArray = new char[20];
            int readLength = reader.read(charArray);
            while (readLength != -1) {
                String newData = new String(charArray, 0, readLength);
                System.out.print(newData);
                readLength = reader.read(charArray);
            }
            System.out.println();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}