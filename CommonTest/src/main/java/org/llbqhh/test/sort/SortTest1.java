package org.llbqhh.test.sort;

public class SortTest1 {
    static int[] nums = {10, 323, 54354, 132, 32, 3, 43, 33, 5938, 12093, 12000, 21, 9, 211, 1000000};

    public static void main(String[] args) {
//selectSort();
        insertionSort();
    }

    /**
     * ѡ������
     */
    public static void selectSort() {
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println("");
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int min = nums[i];
            int k = i;
            for (int j = i + 1; j < length; j++) {
                if (nums[j] < min) {
                    min = nums[j];
                    k = j;
                }
            }
            nums[k] = nums[i];
            nums[i] = min;
        }
        for (int num : nums) {
            System.out.print(num + ",");
        }
    }

    /**
     * ��������
     */
    public static void insertionSort() {
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println("");
        int length = nums.length;
        for (int i = 1; i < length; i++) {
            int inum = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > inum) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = inum;
        }
        for (int num : nums) {
            System.out.print(num + ",");
        }
    }
}
