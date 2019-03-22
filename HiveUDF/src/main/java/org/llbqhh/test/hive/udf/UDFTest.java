package org.llbqhh.test.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by lilibiao on 2017/12/18.
 */
public class UDFTest
        extends UDF{
    public boolean evaluate() {
        return true;
    }
}
