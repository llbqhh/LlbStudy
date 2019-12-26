package org.llbqhh.study.design.pattern.strategy;

/**
 * @author lilibiao
 * @date 2019-12-26 13:47
 */
public class Cat {
    public int height;

    @Override
    public String toString() {
        return "Cat{" +
                "height=" + height +
                '}';
    }

    public Cat(int height) {
        this.height = height;
    }
}
