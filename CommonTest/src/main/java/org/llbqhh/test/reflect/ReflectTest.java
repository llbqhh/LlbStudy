package org.llbqhh.test.reflect;

import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectTest {
    static int time = 1;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
// getNameTest();
// reflectObj();
//  reflectMethod1();
// reflectMethod2();

        reflectMethod32();

// HttpClient httpClient;
    }

    public static void reflectMethod32() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        Class<? extends ReflectExecutor> clazz = re.getClass();
        for (long i = 0; i < time; i++) {
            Field sb = clazz.getDeclaredField("sb");
            sb.setAccessible(true);
            System.out.println(sb.toString());
            System.out.println(sb.getType());
            System.out.println(Utils.invokeMethod(sb.get(sb), "toString").toString());
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时 ： " + (end - start));
    }

    public static void getNameTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        for (long i = 0; i < time; i++) {
            re.getName();
        }
        long end = System.currentTimeMillis();
        System.out.println("����ִ�У�" + (end - start));
    }

    public static void reflectObj() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        long start = System.currentTimeMillis();
        Class<?> classType = Class.forName("ReflectExecutor");
        ReflectExecutor re = (ReflectExecutor) classType.newInstance();
        for (long i = 0; i < time; i++) {
            re.getName();
        }
        long end = System.currentTimeMillis();
        System.out.println("�÷��䴴������" + (end - start));
    }

    public static void reflectMethod1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        Class<? extends ReflectExecutor> clazz = re.getClass();
        Method m1 = clazz.getDeclaredMethod("getName");
        for (long i = 0; i < time; i++) {
            m1.invoke(re);
        }
        long end = System.currentTimeMillis();
        System.out.println("�����÷���ȡ�÷��������ٽ���ѭ�����ԣ�" + (end - start));
    }

    public static void reflectMethod2() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        Class<? extends ReflectExecutor> clazz = re.getClass();
        for (long i = 0; i < time; i++) {
            Method m1 = clazz.getDeclaredMethod("getName");
            m1.invoke(re);
        }
        long end = System.currentTimeMillis();
        System.out.println("ÿ�ε��÷���ʱ���÷���ȡ�÷�������" + (end - start));
    }

    public static void reflectMethod3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        Class<? extends ReflectExecutor> clazz = re.getClass();
        for (long i = 0; i < time; i++) {
            re.getClass().getMethod("print", String.class).invoke(re, "abc");
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时 ： " + (end - start));
    }

    public static void reflectMethod31() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        ReflectExecutor re = new ReflectExecutor();
        Class<? extends ReflectExecutor> clazz = re.getClass();

        for (long i = 0; i < time; i++) {
            re.getClass().getMethod("printList", Seq.class).invoke(re, JavaConversions.asScalaBuffer(Arrays.asList("abc", "abc")));
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时 ： " + (end - start));
    }

    public static void reflectMethod4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();

        try {
            Constructor c1 = ReflectTest.class.getClassLoader().loadClass("org.lilibiao.test.reflect.ReflectExecutor").getDeclaredConstructor(String.class);
            Object re1 = c1.newInstance("ab-");
            for (long i = 0; i < time; i++) {
                re1.getClass().getMethod("print", String.class).invoke(re1, "abc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时 ： " + (end - start));
    }
}
