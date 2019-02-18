package org.llbqhh.test.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadAndWrite {
    public static void main(String[] args)
            throws Exception {
        String path = "E:\\wordCount3.log";
        getAllDirectories("E:\\学习\\牛津电子词典");
        System.out.println("directory:" + directory.size());
        analysisOneFile(new File(path));
        wordCounts = sortMapByValue(wordCounts);
//        wordCounts.forEach((k, v) -> {
//            System.out.println(k+" : 出现次数["+v+"]");
//            String mean = directory.get(k.toLowerCase());
//            if (mean.startsWith("/")){
//                mean = mean.replaceFirst("/[^/]*/","");
//            }
//            System.out.println(mean);
//            System.out.println("=============");
//        });
        writeFile("E:\\wordCount5.log");
        System.out.println(wordCounts.size());
    }

    public static void writeFile(String fileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName, true);
// GZIPInputStream gzin = new GZIPInputStream(fin);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        BufferedWriter bw = new BufferedWriter(osw);
//        bw.write("===================\r\n");
        wordCounts.forEach((k, v) -> {
            try {
                bw.write(k.toLowerCase() + " : [" + v + "]\r\n");
//                String mean = directory.get(k.toLowerCase());
//                if (mean.startsWith("/")){
//                    mean = mean.replaceFirst("/[^/]*/","");
//                }
//                bw.write(mean+"\r\n");
//                bw.write("===================\r\n");
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

    private static Map<String, Integer> wordCounts = new HashMap<>();

    private static void analysisOneFile(File file)
            throws IOException {
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), "utf-8"); // 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            String[] words = lineTxt.split("\t");
            if (!(words[0].matches("[a-z]+") || words[0].matches("[A-Z][a-z]+"))) {
                continue;
            }
            if (!directory.containsKey(words[0].toLowerCase())) {
                continue;
            }
            int count = Integer.parseInt(words[1].trim());
            wordCounts.put(words[0], count);
        }
        bufferedReader.close();
        read.close();
    }

    /**
     * 使用 Map按value进行排序
     *
     * @param
     * @return
     */
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    public static void getAllDirectories(String dir)
            throws IOException {
        File rootDir = new File(dir);
        File[] files = rootDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                analysisOneDirectory(file);
            } else {
                getAllDirectories(file.getAbsolutePath());
            }
        }
    }

    private static Map<String, String> directory = new HashMap<>();

    private static void analysisOneDirectory(File file)
            throws IOException {
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), "gbk"); // 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        String word = null;
        String mean;
        int num = 0;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            num++;
            if (num % 2 != 0) {
                word = lineTxt;
            } else {
                mean = lineTxt;
                directory.put(word, mean);
            }
        }
        bufferedReader.close();
        read.close();
    }
}

class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
        return me2.getValue().compareTo(me1.getValue());
    }
}