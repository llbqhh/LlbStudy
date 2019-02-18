package org.llbqhh.test.io;

import java.util.Scanner;

public class Scanf {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println("�������ˣ�[" + line + "]");
            if (line.equals("quit")) {
                System.out.println("�ݰ�");
                break;
            }
        }
        sc.close();
    }
}
