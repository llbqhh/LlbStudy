package org.llbqhh.test.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Meta {
    public static void main(String[] args) throws SQLException {
        String sql = args[0];
        Connection conn = null; //ConnManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmt = ps.getMetaData();
        int columnCount = rsmt.getColumnCount();
        while (rs.next()) {
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i <= columnCount; i++) {
                sb.append("," + rsmt.getColumnName(i) + ":" + rs.getString(i));
            }
            System.out.println(sb.toString().replaceFirst(",", ""));
            sb = null;
        }
        rs.close();
        ps.close();
        conn.close();
    }
}
