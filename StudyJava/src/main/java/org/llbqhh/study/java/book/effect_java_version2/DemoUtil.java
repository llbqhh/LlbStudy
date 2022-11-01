package org.llbqhh.study.java.book.effect_java_version2;

/**
 * @author lilibiao
 * @date 2020/4/19 5:33 下午
 */
// Noninstantiable utility class
public class DemoUtil {
    // Suppress default constructor for noninstantiability
    private DemoUtil() {
        throw new AssertionError();
    }
}
