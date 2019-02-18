package org.llbqhh.test.hash;

import java.util.HashMap;
import java.util.Map;

public class HashTest {
    public static void main(String[] args) {
        String[] strs = {"GA8Mfaaa", "e4R5Blaa", "Y92RLlaa", "sCvEytaa", "ydDspvaa",
                "ofLCCCaa", "uemC0Daa", "CbgFKKaa", "d0XRbNaa", "hUzU9Saa"};
        for (String str : strs) {
            System.out.println(str.hashCode());
        }

        //�����⣺
        System.out.println("��ׯ".hashCode());
        System.out.println("����".hashCode());
        //��֪�����ַ���hashֵ��ͬ�����ʷ���HashMap���Ƿ�Ḳ�ǣ�
        Map<String, String> mapTest = new HashMap<String, String>();
        mapTest.put("��ׯ", "");
        mapTest.put("����", "");
        System.out.println(mapTest);
    }
}
