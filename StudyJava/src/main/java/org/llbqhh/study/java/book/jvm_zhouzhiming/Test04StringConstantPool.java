package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 字符串常量池相关测试
 */
public class Test04StringConstantPool {
    public static void main(String[] args) {
        // 首先会将【计算机】和【软件】两个字符串存入字符串常量池
        // str1这个字符串对象在堆中创建
        // str1.intern方法会在常量池中创建【计算机软件】的常量，但因为堆中已经有了，所以不再重新创建，而是直接只想str1对象
        // 所以在这里str1.intern() == str1 的结果是true
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1); // true
        System.out.println(str1.intern() == "计算机软件"); // true

        // 这个例子首先strTmp这一句就已经将【测试】这个字符串在常量池中创建了
        // str2则是在常量池创建了【测】和【试】两个常量后，在堆中又创建了一个【测试】的字符串对象
        // 在这里str2.intern()返回的是常量池中的字符串对象，和str2不同，所以这里返回false
        String strTmp = "测试";
        String str2 = new StringBuilder("测").append("试").toString();
        System.out.println(str2.intern() == str2); // false
        System.out.println(str2.intern() == "测试"); // true

        // 在虚拟机启动过程中，java会被加入到字符串常量池
        // 所以这里结果和str2相同
        String str3 = new StringBuilder("ja").append("va").toString();
        System.out.println(str3.intern() == str3); // false
        System.out.println(str3.intern() == "java"); // true
        /*
        运行结果：
        true
        true
        false
        true
        false
        true
         */
    }
}
