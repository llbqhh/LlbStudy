package org.llbqhh.study.design.pattern.strategy;

/**
 * dog比较策略2
 * @author lilibiao
 * @date 2019-12-26 13:51
 */
public class DogWeightComparator implements Comparator<Dog> {
    @Override
    public int compare(Dog o1, Dog o2) {
        if (o1.weight < o2.weight) return -1;
        else if (o1.weight > o2.weight) return 1;
        else return 0;
    }
}
