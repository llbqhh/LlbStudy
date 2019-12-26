package org.llbqhh.study.design.pattern.strategy;

/**
 * @author lilibiao
 * @date 2019-12-26 13:44
 */
public class Dog {
    public int weight;
    public int height;

    public Dog(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "weight=" + weight +
                ", height=" + height +
                '}';
    }
}
