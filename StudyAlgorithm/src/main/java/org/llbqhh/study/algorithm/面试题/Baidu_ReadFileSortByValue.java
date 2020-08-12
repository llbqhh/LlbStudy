package org.llbqhh.study.algorithm.面试题;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
输入文件，每行两列，空格分隔  <type value>
A 0.8
B 0.5
D 0.6
C 0.4
A 0.3
C 0.7
B 0.5
A 0.75
...

输出要求：
1 按value从大到小排序，如果value相同忽略排序
2 同type 的需要打散，之间间隔2个元素
3 对于最终剩余元素不能满足打散要求的直接输出

demo输出为：
A-0.8 C-0.7 D-0.6 A-0.75 B-0.5 C-0.4 A-0.3 B-0.5

 */

public class Baidu_ReadFileSortByValue {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\Administrator\\Desktop\\t.txt";
        System.out.println(new Baidu_ReadFileSortByValue().readFile(fileName));
    }

    public String readFile(String fileName) {

        if (fileName == null || !new File(fileName).exists()){
            return "";
        }

        List<String> arrayList = new ArrayList<>();
        TreeMap<String, String> map = new TreeMap<>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                str = str.replace(" ", "-") ;
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 没有输入
        if (arrayList.size() == 0) {
            return "";
        }

        // 转成数组
        String[] arr = new String[arrayList.size()];
        arrayList.toArray(arr);

        // 按分倒排序
        Arrays.sort(arr, (o1, o2) -> o2.split("-")[1].compareTo(o1.split("-")[1]));

        // 临时链表用于存放分值相近类型相同的元素
        LinkedList<String> tmpList = new LinkedList<>();

        // 返回结果
        String[] res = new String[arr.length];

        int i = 0; // res指针
        int j = 0; // 倒排后的arr指针
        while (i < res.length && j < arr.length) {
            if (i == 0) {
                res[0] = arr[0];
                i++;
                j++;
            } else if (i == 1) {
                if (!tmpList.isEmpty() && !isSameType(tmpList.getFirst(), res[i-1])) {
                    res[i] = tmpList.pollFirst();
                    i++;
                    continue;
                }
                if (isSameType(arr[j], res[i-1])) {
                    tmpList.add(arr[j]);
                    j++;
                } else {
                    res[i] = arr[j];
                    i++;
                    j++;
                }
            } else if (i > 1) {
                if (!tmpList.isEmpty() && !isSameType(tmpList.getFirst(), res[i-1]) && !isSameType(tmpList.getFirst(), res[i-2])) {
                    res[i] = tmpList.pollFirst();
                    i++;
                    continue;
                }
                if (!isSameType(arr[j], res[i-1]) && !isSameType(arr[j], res[i-2])) {
                    res[i] = arr[j];
                    i++;
                    j++;
                } else {
                    tmpList.add(arr[j]);
                    j++;
                }
            }
        }

        while (i < res.length) {
            res[i] = tmpList.pollFirst();
            i++;
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < res.length; k++) {
            sb.append(" ").append(res[k]);
        }
        return sb.substring(1).toString();
    }

    public boolean isSameType(String a, String b) {
        try {
            return a.split("-")[0].equals(b.split("-")[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }

}
