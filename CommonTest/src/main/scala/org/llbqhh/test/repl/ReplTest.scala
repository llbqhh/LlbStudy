package org.llbqhh.test.repl

import java.io.{BufferedReader, PrintWriter}

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.{ILoop}

object ReplTest {
  def main(args: Array[String]): Unit = {
    // new config
    val settings = new Settings
    // 使用java的classpath
    settings.usejavacp.value=true
    // 创建repl的iLoop
    val iLoop = new ILoop(null.asInstanceOf[BufferedReader], new PrintWriter(System.out))
    iLoop.settings = settings
    iLoop.createInterpreter()
    val iMain = iLoop.intp

    // 调用Interpret方法
    iMain.interpret("val abc = \"abctest\"")
    iMain.interpret("println(abc)")
    iMain.interpret("println(\"test\")")

    iMain.close()
    iLoop.closeInterpreter()
  }
}
