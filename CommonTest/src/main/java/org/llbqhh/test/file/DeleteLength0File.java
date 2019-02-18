package org.llbqhh.test.file;

import java.io.File;
import java.io.IOException;

public class DeleteLength0File {
    public static void main(String[] args) throws IOException {
        File tmpFilePathDir = new File("./tmpFiles/");
        if (!tmpFilePathDir.exists()) {
            tmpFilePathDir.mkdir();
        } else {
            for (File tmpFile : tmpFilePathDir.listFiles()) {
                if (tmpFile.length() == 0) {
                    tmpFile.delete();
                    System.out.println(tmpFile + "'s length is 0,delete...");
                }
            }
        }
        new File("./tmpFiles/InterestAppTmp20150406.log").createNewFile();
    }
}
