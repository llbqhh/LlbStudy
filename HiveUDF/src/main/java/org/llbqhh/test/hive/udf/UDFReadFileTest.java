package org.llbqhh.test.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by lilibiao on 2017/12/18.
 */
public class UDFReadFileTest
        extends UDF{
    public String evaluate() {
        return evaluate("/");
    }

    public String evaluate(String path) {
        File file = new File(path);
        return Arrays.stream(file.listFiles()).map(File::getAbsolutePath).collect(Collectors.joining(";"));
    }

    public static void main(String[] args) {
        System.out.println(new UDFReadFileTest().evaluate());
    }
}
