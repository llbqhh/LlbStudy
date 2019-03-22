package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;

public class TimeParse extends GenericUDF {
    public TimeParse() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 1) {
            throw new UDFArgumentLengthException("The function time_parse(timeStr) takes exactly 1 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        assert arguments.length == 1;

        LongWritable lw = new LongWritable(0L);
        if(arguments[0].get() == null) {
            return lw;
        } else {
            String timeStr = arguments[0].get().toString();
            String pattern = "(\\d+):(\\d+):(\\d+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(timeStr);
            if(m.find()) {
                long result = Long.parseLong(m.group(1)) * 3600L + Long.parseLong(m.group(2)) * 60L + Long.parseLong(m.group(3));
                LongWritable res = new LongWritable(result);
                return res;
            } else {
                return lw;
            }
        }
    }

    public String getDisplayString(String[] children) {
        return "Usage: time_parse(String timeStr)";
    }
}
