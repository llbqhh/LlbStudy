package org.llbqhh.test.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 记录日志
 *
 * @author hadoop
 */
public class Log {
    //
    public static String jarPath;

    static {
        String path = Log.class.getProtectionDomain().getCodeSource().getLocation()
                .getFile();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        java.io.File jarFile = new java.io.File(path);
        // String jarName = jarFile.getName();
        java.io.File parent = jarFile.getParentFile();
        if (parent != null) {
            jarPath = parent.getAbsolutePath();
        }
    }

    //　private static final File log = new File(jarPath+"/PCRLogs/PCRLog"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".log");
    private static final File log = new File(jarPath + "/logs/log" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".log");
    private static final File logPath = new File(jarPath + "/logs/");

    public static void write(String line) {
        appendLog(line);
    }

    private static void appendLog(String newLog) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " : " + newLog);
        synchronized (log) {
            appendLog(log, newLog);
        }
    }

    private static void appendLog(File log, String newLog) {
        try {
            if (!log.exists()) {
                logPath.mkdirs();
                log.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(log, true));
            out.append(newLog + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
