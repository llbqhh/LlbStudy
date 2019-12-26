package org.llbqhh.study.design.pattern.strategy;

/**
 * cat比较策略
 * @author lilibiao
 * @date 2019-12-26 13:51
 */
public class CatComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat o1, Cat o2) {
        if (o1.height < o2.height) return -1;
        else if (o1.height > o2.height) return 1;
        else return 0;
    }
}
