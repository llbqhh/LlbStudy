package org.llbqhh.study.algorithm.leetcode.字符串;

/*
给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。

注意：

num1 和num2 的长度都小于 5100.
num1 和num2 都只包含数字 0-9.
num1 和num2 都不包含任何前导零。
你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/add-strings
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class L415_字符串相加 {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        // 两个指针从尾部开始
        int i1 = num1.length() - 1;
        int i2 = num2.length() - 1;
        // 进位标志
        int add = 0;
        // 从末尾开始按位计算
        while (i1 >= 0 || i2 >= 0 || add > 0) {
            int x = 0;
            int y = 0;
            if (i1 >= 0) {
                x = num1.charAt(i1) - '0';
            }
            if (i2 >= 0) {
                y = num2.charAt(i2) - '0';
            }
            int result = x + y + add;
            sb.append(result % 10);

            // 重置进位标志
            if (result >= 10) {
                add = 1;
            } else {
                add = 0;
            }
            i1--;
            i2--;
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(('0' + '0'));
        System.out.println("0".charAt(0));
        System.out.println("5".charAt(0));
        System.out.println(new L415_字符串相加().addStrings("123", "123"));
        System.out.println(new L415_字符串相加().addStrings("5", "16"));
        System.out.println(new L415_字符串相加().addStrings("1", "9"));
    }
}
