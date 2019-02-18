package org.llbqhh.test.repl;

import scala.tools.nsc.Settings;
import scala.tools.nsc.interpreter.ILoop;
import scala.tools.nsc.interpreter.IMain;
import scala.tools.nsc.settings.MutableSettings;
import java.io.PrintWriter;

public class ScalaReplTest {
    public static void main(String[] args) {
        // new config
        Settings settings = new Settings();
        // 使用java的classpath
        MutableSettings.BooleanSetting b = (MutableSettings.BooleanSetting) settings.usejavacp();
        b.v_$eq(true);
        settings.scala$tools$nsc$settings$StandardScalaSettings$_setter_$usejavacp_$eq(b);
        // 创建repl的iLoop
        ILoop iLoop = new ILoop((java.io.BufferedReader) null, new PrintWriter(System.out));
        iLoop.settings_$eq(settings);
        iLoop.createInterpreter();
        IMain iMain = iLoop.intp();

        // 调用Interpret方法
        iMain.interpret("val abc = \"abctest\"");
        iMain.interpret("println(abc)");
        iMain.interpret("println(\"test\")");

        iMain.close();
        iLoop.closeInterpreter();
    }
}
