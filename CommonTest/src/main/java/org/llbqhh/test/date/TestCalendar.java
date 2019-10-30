package org.llbqhh.test.date;

import java.util.Calendar;

/**
 * 测试Calendar的getInstance方法是否是创建新的Calendar对象
 */
public class TestCalendar {
    public static void main(String[] args) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        // 观察是否是同一个对象
        System.out.println("两个Calendar是否是同一个对象：" + (c1 == c2));
        c1.add(Calendar.DAY_OF_YEAR, -10);
        c2.add(Calendar.DAY_OF_YEAR, +10);
        System.out.println("c1:" + c1.getTime());
        System.out.println("c2:" + c2.getTime());
        /*
        多次调用Calendar.getInstance获取的对象是不同的
        观察源码可以看出，此方法内部是调用createCalendar方法创建了一个新的Calendar

        运行结果：
        两个Calendar是否是同一个对象：false
        c1:Sun Oct 20 14:35:39 CST 2019
        c2:Sat Nov 09 14:35:39 CST 2019
         */
    }
}
