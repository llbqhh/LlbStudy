package org.llbqhh.test.disruptor.background;

/**
 * @Author lilibiao
 * @Date 2021/3/16
 * @Description:
 */
public class CacheLineEffect {
    //考虑一般缓存行大小是64字节，一个 long 类型占8字节
    static  long[][] arr;

    public static void main(String[] args) {

        int[][] rowCol = {
                {00,01,02,03,04,05,06,07},
                {10,11,12,13,14,15,16,17},
                {20,21,22,23,24,25,26,27},
                {30,31,32,33,34,35,36,37},
                //...
        };

        //输出 4
        System.out.println(rowCol[0][4]);

        int rowNums = 1024 * 1024 ;
        arr = new long[rowNums][];
        for (int i = 0; i < rowNums; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < rowNums; i+=1) {
            for(int j =0; j< 8;j++){
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i+=1) {
            for(int j =0; j< rowNums;j++){
                sum = arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}
