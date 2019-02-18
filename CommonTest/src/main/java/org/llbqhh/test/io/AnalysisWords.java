package org.llbqhh.test.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class AnalysisWords {
    public static void main(String[] args)
            throws Exception {
        String path = "E:\\sources";
        analysisSourceWord(path);
        writeFile("E:\\wordCount2.log");
        System.out.println(wordCounts.size());
    }

    public static void writeFile(String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName, true);
//      GZIPInputStream gzin = new GZIPInputStream(fin);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        BufferedWriter bw = new BufferedWriter(osw);

        wordCounts.forEach((k, v) -> {
            try {
                bw.write(k + "\t" + v + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //bin.close();
        bw.close();
// gzin.close();
        osw.close();
        fos.close();
    }

    public static void analysisSourceWord(String dir)
            throws IOException {
        File rootDir = new File(dir);
        File[] files = rootDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                analysisOneFile(file);
            } else {
                analysisSourceWord(file.getAbsolutePath());
            }
        }
    }

    private static int analysisNum = 0;
    private static Map<String, Integer> wordCounts = new HashMap<>();

    private static void analysisOneFile(File file)
            throws IOException {
        analysisNum++;
        if (!file.getAbsolutePath().endsWith(".java") && !file.getAbsolutePath().endsWith(".scala")) {
            return;
        }
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), "utf-8"); // 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            for (String word : lineTxt.split(" ")) {
                if (!word.matches("[a-zA-Z]+")) {
                    continue;
                }
                if (wordCounts.computeIfPresent(word, (k, v) -> v + 1) == null) {
                    wordCounts.put(word, 1);
                }
            }
        }
        bufferedReader.close();
        read.close();
        if (analysisNum % 500 == 0) {
            System.out.println(String.format("analysisOneFile[%s],now:[%s]", analysisNum, file.getAbsolutePath()));
        }
    }
}
