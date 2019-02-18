package org.llbqhh.stock;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockMain {
    private static long inputNum = 0;
//    private static String className = "com.mysql.jdbc.Driver";
    private static String className = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws Exception {
        System.out.println("程序启动。。。");
        truncateTable();
        readStockData();
        System.out.println("程序结束。。。");
    }

    private static void truncateTable() throws Exception {
        System.out.println("清空表中原有数据。。。。");
        Class.forName(className);
        Properties properties = loadProperties();
        Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        String sql = "truncate table " + properties.getProperty("tableName");
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
        conn.close();
        System.out.println("清空表中原有数据ok");
    }

    private static void readStockData() throws Exception {
        File dir = new File(jarPath);
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".csv"));
        System.out.println("读取csv文件，文件总数：" + files.length);
        if (files.length == 0) {
            System.out.println("当前路径没有csv文件！");
            return;
        }
        //TODO
        /**
         * 1、遍历所有csv结尾的文件
         * ２、行数正确，则放入数组中
         * ３、每个文件写入一次mysql
         */
        int i = 1;
        for (File file : files) {
            System.out.println("开始读取第" + i + "个文件：" + file.getName());
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8"); // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            List<String[]> data = new ArrayList<>();
            boolean isHead = true;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                //是否需要跳过表头
                if (isHead) {
                    isHead = false;
                    continue;
                }
                String[] line = lineTxt.split(",");
                if (line.length != 20) {
                    continue;
                }
                data.add(line);
            }
            bufferedReader.close();
            read.close();
            System.out.println("开始载入数据库第" + i + "个文件：" + file.getName());
            loadAllStockData(data);
            i++;
        }
    }

    private static void loadAllStockData(List<String[]> data) throws Exception {
        Class.forName(className);
        Properties properties = loadProperties();
        Connection conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        String sql = "insert " + properties.getProperty("tableName") + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()) {
//            System.out.println(rs.getString(1));
//        }
        int batchNum = 0;
        for (String[] line : data) {
            int cNum = line.length;
            for (int i = 0; i < cNum; i++) {
                ps.setString(i + 1, line[i]);
            }
            ps.addBatch();
            batchNum++;
            inputNum++;
            if (batchNum % 2000 == 0) {
                System.out.println("当前文件已入库" + batchNum + "条，所有文件共入库" + inputNum + "条");
                ps.executeBatch();
            }
        }
        ps.executeBatch();
        System.out.println("当前文件完成，入库" + batchNum + "条，所有文件共入库" + inputNum + "条");
        ps.close();
        conn.close();
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(jarPath + File.separator + "mysql.conf"));
        Properties p = new Properties();
        p.load(in);
        return p;
    }

    public static String jarPath;

    static {
        String path = StockMain.class.getProtectionDomain().getCodeSource().getLocation()
                .getFile();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            Logger.getLogger(StockMain.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        java.io.File jarFile = new java.io.File(path);
        java.io.File parent = jarFile.getParentFile();
        if (parent != null) {
            jarPath = parent.getAbsolutePath();
        }
    }
}
