package org.llbqhh.test.spark;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by guoliming on 2015/11/10.
 */
public class CommonUtil {
    public static final String COLUMNS = "columns";

    static char[] hexChar = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 生成32位md5编码
     *
     * @return
     */
    public static String generateMD5Code() {
        String reStr;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(UUID.randomUUID().toString().getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("generateMD5Code error : " + e.getMessage());
        }
        return reStr;
    }

    public static String generateAppName() {
        String generateMD5Code = generateMD5Code();
        int max = 26;
        int min = 0;
        Random random = new Random();

        int index = random.nextInt(max) % (max - min + 1) + min;
        return hexChar[index] + generateMD5Code.substring(8, 23);
    }

    /**
     * 把毫秒转化成日期
     *
     * @param millSec(毫秒数)
     * @return
     */
    public static String longToDateStr(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    public static Object invokeMethod(Object o, String name) {
        return invokeMethod(o, name, new Class[]{}, new Object[]{});
    }

    public static Object invokeMethod(Object o, String name, Class[] argTypes, Object[] params) {
        try {
            return o.getClass().getMethod(name, argTypes).invoke(o, params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Object invokeStaticMethod(Class c, String name, Class[] argTypes, Object[] params) {
        try {
            return c.getMethod(name, argTypes).invoke(null, params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Object invokeStaticMethod(Class c, String name) {
        return invokeStaticMethod(c, name, new Class[]{}, new Object[]{});
    }
}
