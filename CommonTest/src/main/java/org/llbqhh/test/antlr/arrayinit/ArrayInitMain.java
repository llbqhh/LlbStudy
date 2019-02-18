package org.llbqhh.test.antlr.arrayinit;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.spark.sql.catalyst.parser.ANTLRNoCaseStringStream;
import org.junit.Test;

import java.io.IOException;

/**
 * 所需的类基本都是使用antlr从.g4文件生成的
 */
public class ArrayInitMain {
    @Test
    public void testPrintParseTree() {
        String input = "{1 , {2, 3}, 4}";

        ANTLRNoCaseStringStream in = new ANTLRNoCaseStringStream(input);

        ArrayInitLexer lexer = new ArrayInitLexer(in);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        ArrayInitParser parser = new ArrayInitParser(tokenStream);

        ParseTree parseTree = parser.init();

        System.out.println(parseTree.toStringTree(parser));
    }

    @Test
    public void testTranslate() {
        String input = "{1, 3, 4}";

        ANTLRNoCaseStringStream in = new ANTLRNoCaseStringStream(input);

        ArrayInitLexer lexer = new ArrayInitLexer(in);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        ArrayInitParser parser = new ArrayInitParser(tokenStream);

        ParseTree parseTree = parser.init();

        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new ShortToUnicodeString(), parseTree);

        System.out.println();
    }
}

class ShortToUnicodeString extends  ArrayInitBaseListener {
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        super.enterInit(ctx);
        System.out.print("\"");
    }

    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        super.exitInit(ctx);
        System.out.print("\"");
    }

    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        super.enterValue(ctx);
        int value = Integer.valueOf(ctx.INT().getText());
        System.out.printf("\\u%04x", value);
    }
}
