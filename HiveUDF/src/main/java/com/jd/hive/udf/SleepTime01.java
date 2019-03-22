package com.jd.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

import java.util.Objects;

public class SleepTime01 extends GenericUDF {

    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        if(objectInspectors.length != 1) {
            throw new UDFArgumentLengthException("The function time_parse(timeStr) takes exactly 1 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        DeferredObject deferredObjects1 = deferredObjects[0];
        String sleepSecondsStr = Objects.toString(deferredObjects1.get(), "0");
        long sleepSeconds = Long.parseLong(sleepSecondsStr);
        try {
            Thread.currentThread().sleep(sleepSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Text(String.format("sleep %s ok!", sleepSeconds));
    }

    @Override
    public String getDisplayString(String[] strings) {
        return null;
    }
}
