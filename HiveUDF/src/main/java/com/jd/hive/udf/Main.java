package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.jd.hive.udf.BillJsonToList;

public class Main {
    static BillJsonToList billJsonToList = new BillJsonToList();

    public Main() {
    }

    public static void main(String[] args) throws Exception {
        String code = args[0];
        String nt = "0";
        String str = args[1];
        System.out.print(str);
        String str1 = billJsonToList.evaluate(code, nt, str);
        System.out.print(str1);
    }
}
