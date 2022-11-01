package org.llbqhh.study.bigdata.sqlparse;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @Author lilibiao
 * @Date 2020/11/4
 * @Description:
 */
public class SqlParseTest {

    private static List<SQLStatement> getSQLStatementList(String sql) {
        String dbType = JdbcConstants.MYSQL;
        return SQLUtils.parseStatements(sql, dbType);
    }

    public static void main(String[] args) {
        String sqlTemplate1 = "select ${a},count(1),${a}\n" +
                "from abc\n" +
                "where a=${a} and e='2' and b=${b} and (c is null or d is not null)\n" +
                "group by ${a},${b},d\n" +
                "order by ${a},${b} desc,${a},d";
        String sqlTemplate2 = "select ${a},${b},count(1) \n" +
                "from\n" +
                "group by ${a},${b}\n" +
                "where a=${a} and b=${b}";
        String sql = "select age a,name n from student s inner join (select id,name from score where sex='女') temp on sex='男' and temp.id in(select id from score where sex='男') where student.name='zhangsan' group by student.age order by student.id ASC;";

        replaceParamAndPrintSql(sqlTemplate1, null, null);
    }

    public static void replaceParamAndPrintSql(String sqlTemplate, String aParam, String bParam) {
        sqlTemplate = sqlTemplate.replace("${a}", aParam == null ? "null" : aParam).replace("${b}", bParam == null ? "null" : bParam);
        System.out.println("SQL语句为：" + sqlTemplate);
        //格式化输出
        String result = SQLUtils.format(sqlTemplate, JdbcConstants.MYSQL);
        System.out.println("格式化后输出：\n" + result);
        System.out.println("*********************");
        List<SQLStatement> sqlStatementList = getSQLStatementList(sqlTemplate);
        //默认为一条sql语句
        SQLStatement stmt = sqlStatementList.get(0);

        for (SQLObject sqlObject : stmt.getChildren()) {
            System.out.println(sqlObject.toString());
        }

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        stmt.accept(visitor);
        System.out.println("数据库类型\t\t" + visitor.getDbType());

        //获取字段名称
        System.out.println("查询的字段\t\t" + visitor.getColumns());

        //获取表名称
        System.out.println("表名\t\t\t" + visitor.getTables().keySet());

        System.out.println("条件\t\t\t" + visitor.getConditions());

        System.out.println("group by\t\t" + visitor.getGroupByColumns());

        System.out.println("order by\t\t" + visitor.getOrderByColumns());
    }
}
