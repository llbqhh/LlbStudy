package org.llbqhh.test.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.IntWritable;

//UDAF是輸入多個數據行，產生一個數據行
//使用者自定義的UDAF必須是繼承了UDAF，且內部包含多個實現了exec的靜態類
public class UDAFTest extends UDAF {
    public static class MaxiNumberIntUDAFEvaluator implements UDAFEvaluator {
        // 最終結果
        private IntWritable result;

        // 負責初始化計算函式並設定它的內部狀態，result是存放最終結果的
        @Override
        public void init() {
            result = null;
        }

        // 每次對一個新值進行聚集計算都會呼叫iterate方法
        public boolean iterate(IntWritable value) {
            if (value == null)
                return false;
            if (result == null)
                result = new IntWritable(value.get());
            else
                result.set(Math.max(result.get(), value.get()));
            return true;
        }

        // Hive需要部分聚集結果的時候會呼叫該方法
        // 會返回一個封裝了聚集計算當前狀態的物件
        public IntWritable terminatePartial() {
            return result;
        }

        // 合併兩個部分聚集值會呼叫這個方法
        public boolean merge(IntWritable other) {
            return iterate(other);
        }

        // Hive需要最終聚集結果時候會呼叫該方法
        public IntWritable terminate() {
            return result;
        }
    }
}