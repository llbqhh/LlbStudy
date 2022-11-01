package org.llbqhh.study.bigdata.sqlparse;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNullExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLSelectParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.util.Iterator;
import java.util.Objects;

/**
 * @Author lilibiao
 * @Date 2020/11/4
 * @Description: 使用此类前，先将sql中的占位符（例如${a}）全部替换成字符串，如果没有指定字符串（例如选填参数），则将占位符替换为null
 */
public class SelectStatementParser extends SQLStatementParser {

    /**
     * 正常使用中没有用户会将 null 值用作查询条件（ is null 和 is not null 的情况我们做特殊判断处理），
     * 所以我们将未替换的占位符简单替换为 null，这样可以复用词法定义 SQLNullExpr，
     * 如果后期有需要，我们可以修改词法定义，增加【未指定字段】，并且替换此处的类型
     * 后面的处理中，会将 select 语句解析结果中所有 SQLNullExpr 字段从sql语句中去掉，如果一个 where 语句中的所有字段均被去掉，则 where 语句本身也会被剔除
     */
    Class sqlNullExpr = SQLNullExpr.class;

    public SelectStatementParser(String sql) {
        super(sql);
    }

    public SelectStatementParser(String sql, String dbType) {
        super(sql, dbType);
    }

    public SelectStatementParser(SQLExprParser exprParser) {
        super(exprParser);
    }

    protected SelectStatementParser(Lexer lexer, String dbType) {
        super(lexer, dbType);
    }

    @Override
    public SQLStatement parseSelect() {
        SQLSelectParser selectParser = createSQLSelectParser();
        SQLSelect select = selectParser.select();
        // 处理未替换占位符的select语句，将未替换的字段去掉
        handleEmptyParam(select.getQueryBlock());
        return new SQLSelectStatement(select, getDbType());
    }

    /**
     * 处理未替换占位符的select语句，将未替换的字段去掉
     *
     * @param queryBlock
     */
    private void handleEmptyParam(SQLSelectQueryBlock queryBlock) {
        // 处理select
        handleSelectClause(queryBlock);

        // 处理where
        handleWhereClause(queryBlock);

        // 处理group by
        handleGroupByClause(queryBlock);

        // 处理order by
        handleOrderClause(queryBlock);
    }

    private void handleSelectClause(SQLSelectQueryBlock queryBlock) {
        if (queryBlock.getSelectList() == null) {
            return;
        }
        Iterator<SQLSelectItem> selectItemIterator = queryBlock.getSelectList().iterator();
        while (selectItemIterator.hasNext()) {
            SQLSelectItem selectItem = selectItemIterator.next();
            if (selectItem.getExpr().getClass() == sqlNullExpr) {
                selectItemIterator.remove();
            }
        }
    }

    private void handleWhereClause(SQLSelectQueryBlock queryBlock) {
        queryBlock.setWhere(handleWhereClauseInternal(queryBlock.getWhere()));
    }

    private SQLExpr handleWhereClauseInternal(SQLExpr whereExpr) {
        if (whereExpr == null) {
            return null;
        }
        SQLBinaryOpExpr whereBinaryOpExpr = (SQLBinaryOpExpr) whereExpr;

        SQLExpr leftExpr = whereBinaryOpExpr.getLeft();
        if (leftExpr.getClass() == SQLBinaryOpExpr.class) {
            leftExpr = handleWhereClauseInternal(leftExpr);
        }
        SQLExpr rightExpr = whereBinaryOpExpr.getRight();
        if (rightExpr.getClass() == SQLBinaryOpExpr.class) {
            rightExpr = handleWhereClauseInternal(rightExpr);
        }

        if (!Objects.isNull(leftExpr) && leftExpr.getClass() == SQLIdentifierExpr.class
                && whereBinaryOpExpr.getOperator() != SQLBinaryOperator.IsNot
                && whereBinaryOpExpr.getOperator() != SQLBinaryOperator.Is) {
            if (rightExpr.getClass() == sqlNullExpr) {
                // 过滤掉
                return null;
            }
        }

        if (leftExpr == null) {
            whereExpr = rightExpr;
        }

        if (rightExpr == null) {
            whereExpr = leftExpr;
        }

        if (leftExpr != null && rightExpr != null) {
            whereBinaryOpExpr.setLeft(leftExpr);
            whereBinaryOpExpr.setRight(rightExpr);
        }

        return whereExpr;
    }

    private void handleGroupByClause(SQLSelectQueryBlock queryBlock) {
        if (queryBlock.getGroupBy() == null) {
            return;
        }
        Iterator<SQLExpr> sqlExprIterator = queryBlock.getGroupBy().getItems().iterator();
        while (sqlExprIterator.hasNext()) {
            SQLExpr selectItem = sqlExprIterator.next();
            if (selectItem.getClass() == sqlNullExpr) {
                sqlExprIterator.remove();
            }
        }
        if (queryBlock.getGroupBy().getItems().size() == 0) {
            queryBlock.setGroupBy(null);
        }
    }

    private void handleOrderClause(SQLSelectQueryBlock queryBlock) {
        if (queryBlock.getOrderBy() == null) {
            return;
        }
        Iterator<SQLSelectOrderByItem> sqlSelectOrderByItemIterator = queryBlock.getOrderBy().getItems().iterator();
        while (sqlSelectOrderByItemIterator.hasNext()) {
            SQLSelectOrderByItem sqlSelectOrderByItem = sqlSelectOrderByItemIterator.next();
            if (sqlSelectOrderByItem.getExpr().getClass() == sqlNullExpr) {
                sqlSelectOrderByItemIterator.remove();
            }
        }
        if (queryBlock.getOrderBy().getItems().size() == 0) {
            queryBlock.setOrderBy(null);
        }
    }
}
