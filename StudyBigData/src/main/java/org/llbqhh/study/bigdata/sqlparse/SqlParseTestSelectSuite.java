package org.llbqhh.study.bigdata.sqlparse;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author lilibiao
 * @Date 2020/11/5
 * @Description:
 */
public class SqlParseTestSelectSuite {
    @Rule
    public TestName testName = new TestName();

    @Test
    public void testSelect01() {
        String sql = "select null as a,count(1),null from table";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table");
    }

    @Test
    public void testSelect02() {
        String sql = "select null,null,null from table";
        String r = dellWithParam(sql);
        assert r.equals("SELECT \n" +
                "FROM table");
    }

    @Test
    public void testWhere01() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table");
    }

    @Test
    public void testWhere02() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null)";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table");
    }

    @Test
    public void testWhere03() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d is null)";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d IS NULL");
    }

    @Test
    public void testWhere04() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d is not null)";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d IS NOT NULL");
    }

    @Test
    public void testWhere05() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123)";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123");
    }

    @Test
    public void testWhere06() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = '12' and (c = null and d = null and d = 123)";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE b = '12'\n" +
                "\tAND d = 123");
    }

    @Test
    public void testGroupBy01() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123) " +
                "group by a,null";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123\n" +
                "GROUP BY a");
    }

    @Test
    public void testGroupBy02() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123) " +
                "group by null,b,null";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123\n" +
                "GROUP BY b");
    }

    @Test
    public void testGroupBy03() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123) " +
                "group by null,null,null";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123");
    }

    @Test
    public void testOrderBy01() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123) " +
                "group by null,b,null " +
                "order by null desc,a,null,b desc,null asc";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123\n" +
                "GROUP BY b\n" +
                "ORDER BY a, b DESC");
    }

    @Test
    public void testOrderBy02() {
        String sql = "select null,count(1),null from table " +
                "where a = null and b = null and (c = null and d = null and d = 123) " +
                "group by null,b,null " +
                "order by null desc,null,null,null desc,null asc";
        String r = dellWithParam(sql);
        assert r.equals("SELECT count(1)\n" +
                "FROM table\n" +
                "WHERE d = 123\n" +
                "GROUP BY b");
    }


    public String dellWithParam(String sql) {
        String[] typeArr = {JdbcConstants.MYSQL, JdbcConstants.HIVE};
        String sqlResult = null;
        Set<String> results = new HashSet<>();
        for (String type : typeArr) {
            System.out.println("==================" + type + ":" + testName.getMethodName() + "================");
            System.out.println("SQL语句为");
            System.out.println(sql);
            SQLStatementParser sqlStatementParser = new SelectStatementParser(sql, type);
            List<SQLStatement> statementList = sqlStatementParser.parseStatementList();
            System.out.println("---------------");
            System.out.println("优化后SQL语句为：");
            System.out.println(SQLUtils.toSQLString(statementList, type));
            sqlResult = SQLUtils.toSQLString(statementList, type);
            results.add(sqlResult);
        }

        // 解析结果需一致
        assert results.size() == 1;

        return sqlResult;
    }
}
