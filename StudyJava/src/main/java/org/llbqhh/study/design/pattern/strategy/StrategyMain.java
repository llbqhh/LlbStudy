package org.llbqhh.study.design.pattern.strategy;

import java.util.Arrays;

/**
 *
 * Comparator用于策略模式，它是一个FunctionalInterface函数式接口
 * 策略模式的核心是把真正执行的方法封装成类（函数）从外部传入，以便在运行时更改
 *
 * @author lilibiao
 * @date 2019-12-26 13:48
 */
public class StrategyMain {
    public static void main(String[] args) {
        Cat[] cats = new Cat[]{new Cat(2), new Cat(3), new Cat(1)};
        Dog[] dogs = new Dog[]{new Dog(2, 4), new Dog(3, 1), new Dog(1, 2)};

        StragegySorter<Cat> catSorter = new StragegySorter<>();
        catSorter.sort(cats, new CatComparator());
        System.out.println(Arrays.toString(cats));

        StragegySorter<Dog> dogSorter = new StragegySorter<>();
        dogSorter.sort(dogs, new DogHeightComparator());
        System.out.println(Arrays.toString(dogs));

        dogSorter.sort(dogs, new DogWeightComparator());
        System.out.println(Arrays.toString(dogs));

        // 函数式接口
        dogSorter.sort(dogs, (o1, o2) -> {
            if ((o1.height + o1.weight) < (o2.height + o2.weight)) return -1;
            else if ((o1.height + o1.weight) > (o2.height + o2.weight)) return 1;
            else return 0;
        });
        System.out.println(Arrays.toString(dogs));
    }
}
