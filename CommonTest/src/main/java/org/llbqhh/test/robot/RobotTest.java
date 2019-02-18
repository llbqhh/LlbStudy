package org.llbqhh.test.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by lilibiao on 2017/9/27.
 */
public class RobotTest {
    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws Exception {
        robot.delay(2000);
        Set<String> youdaoWords = getYoudaoWords();
        System.out.println("youdaoWords size : " + youdaoWords.size());
        for (String youdaoWord : youdaoWords) {
            translateYoudaoWord(youdaoWord);
            System.out.println(youdaoWord);
        }
//        testRobot("test");
        System.out.println("youdaoWords size : " + youdaoWords.size());
    }

    public static void writeFile(Set<String> youdaoWords) throws Exception {
        FileOutputStream fos = new FileOutputStream("E:\\英语学习\\英文技术词汇7414个-1.txt", true);
//　GZIPInputStream gzin = new GZIPInputStream(fin);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
        BufferedWriter bw = new BufferedWriter(osw);
//        bw.write("===================\r\n");
        youdaoWords.forEach((k) -> {
            try {
                bw.write(k.toLowerCase().trim() + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw.close();
        osw.close();
        fos.close();
    }

    public static Set<String> getYoudaoWords()
            throws FileNotFoundException {
        Set<String> youdaoWords = new LinkedHashSet<>();
        Scanner sc = new Scanner(new File("E:\\英语学习\\英文技术词汇4924.txt"));
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (!line.isEmpty()) {
                line = line.split(":")[0].trim();
                youdaoWords.add(line.trim());
            }
        }
        sc.close();
        return youdaoWords;
    }

    public static void testRobot(String test) {
        //鼠标移动
        robot.mouseMove(600, 12);
        robot.delay(500);

//        鼠标左键双击
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(500);

        //退格
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        robot.delay(500);
    }

    public static void translateYoudaoWord(String youdaoWord)
            throws AWTException {
        int len = youdaoWord.length();
        for (int i = 0; i < len; i++) {
            char iChar = youdaoWord.charAt(i);
            int input = KeyEvent.getExtendedKeyCodeForChar(iChar);
            robot.keyPress(input);
            robot.keyRelease(input);
        }

        //ctrl+v
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//        robot.keyRelease(KeyEvent.VK_V);

        robot.delay(1000);

        //enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(5000);
    }
}
