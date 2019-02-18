package org.llbqhh.test.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWrite {
    public static void main(String[] args) throws Exception {
        writeFile("test.log");
    }

    /**
     * @param fileName
     * @throws Exception
     */
    public static void writeFile(String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName, true);
// GZIPInputStream gzin = new GZIPInputStream(fin);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write("test" + "\r\n");

        //bin.close();
        bw.close();
// gzin.close();
        osw.close();
        fos.close();
    }
}
