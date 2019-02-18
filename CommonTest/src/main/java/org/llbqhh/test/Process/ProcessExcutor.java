package org.llbqhh.test.Process;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by lilibiao on 2017/8/16.
 */
public class ProcessExcutor {
    public static void main(String[] args)
            throws IOException {
        String cmd = "java org.lilibiao.test.Process.ProcessCmd";
//        String cmd = "cmd /c cd";

        String path = "E:\\workspace_intellij\\LlbStudy\\LlbTest\\target\\classes";
//        String path = "./";

        runType2(cmd, path);
    }

    public static void runType2(String cmd, String path)
            throws IOException {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(path));
        CommandLine commandLine = CommandLine.parse(cmd);
        executor.execute(commandLine, System.getenv(), new ExecuteResultHandler() {
            @Override
            public void onProcessComplete(int exitValue) {
                System.out.println("onProcessComplete");
            }

            @Override
            public void onProcessFailed(ExecuteException e) {
                System.out.println("ExecuteException");
                e.printStackTrace();
            }
        });
    }

    public static void runType1(String cmd, String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd, null, new File(path));
            processProtectionThread(p);
            p.waitFor();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 此方法用于保护process顺利完成，用两个线程监听process
     **/
    public static void processProtectionThread(Process p) {
        //获取进程的标准输入流
        final InputStream is1 = p.getInputStream();
        //获取进城的错误流
        final InputStream is2 = p.getErrorStream();
        //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
        new Thread() {
            public void run() {
                BufferedReader br1 = null;
                try {
                    br1 = new BufferedReader(new InputStreamReader(is1, "gbk"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    String line1 = null;
                    while ((line1 = br1.readLine()) != null) {
                        if (line1 != null) {
                            //处理标准输出流
                            System.out.println("info : " + line1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                BufferedReader br2 = null;
                try {
                    br2 = new BufferedReader(new InputStreamReader(is2, "gbk"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    String line2 = null;
                    while ((line2 = br2.readLine()) != null) {
                        if (line2 != null) {
                            //处理标准错误流
                            //处理标准输出流
                            System.out.println("error : " + line2);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
