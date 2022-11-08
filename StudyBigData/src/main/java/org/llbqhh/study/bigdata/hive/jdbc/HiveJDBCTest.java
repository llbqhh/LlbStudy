package org.llbqhh.study.bigdata.hive.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class HiveJDBCTest {

    private static String url = "jdbc:hive2://lilibiao.vm.ubuntu:10000/default";
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String user = "lilibiao";
    private static String password = "";
    private static Connection conn = null;

    static {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showDatabases() {

        try {
            Statement stmt = conn.createStatement();
            String sql = "SHOW DATABASES;";
            ResultSet rs = null;
            rs = stmt.executeQuery("show databases");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
