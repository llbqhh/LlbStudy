package org.llbqhh.study.algorithm.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortTest {
    // 相关源码及原理可以参考https://algs4.cs.princeton.edu/21elementary/
    static Integer[] array = {2, 1, 3, 413, 12, 678, 789, 901, 346, 453, 8906, 12, 789, 120, 789, 324, 31, 213, 123, 234, 456};

    @Before
    public void before() {
        for (int i = 0; i < 80; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }

    @Test
    public void testBubbleSort() {
        System.out.println("bubble sort:");
        BubbleSort.bubbleSort(array);
    }

    @Test
    public void testSelectionSort() {
        System.out.println("selection sort:");
        SelectionSort.selectionSort(array);
    }

    @Test
    public void testInsertionSort() {
        System.out.println("insertion sort:");
        InsertionSort.insertionSort(array);
    }

    @Test
    public void testShellSort() {
        System.out.println("shell sort:");
        DiminishingIncrementSort.shellSort(array);
    }

    @Test
    public void testMerge() {
        System.out.println("merge sort:");
        MergeSort.sort(array);
    }

    @Test
    public void testMergeBU() {
        System.out.println("mergeBU sort:");
        MergeSort.sortBU(array);
    }

    @Test
    public void testQuick() {
        System.out.println("quick sort:");
        QuickSort.sort(array);
    }

    @Test
    public void testQuick3way() {
        System.out.println("quick3wqy sort:");
        QuickSort.sort3way(array);
    }

    @Test
    public void testHeap() {
        System.out.println("heap sort:");
        HeapSort.sort(array);
    }

    @After
    public void show() {
        System.out.println("sort result:");
        BaseSort.show(array);
        assert BaseSort.isSorted(array);
    }
}
