package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.IntWritable;

public class PaymentParse extends GenericUDF {
    public PaymentParse() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 3) {
            throw new UDFArgumentLengthException("The function payment_parse(arguments) takes exactly 3 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableIntObjectInspector;
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        assert arguments.length == 3;

        byte ret = 0;
        int couponFlag = 0;
        if(arguments[0].get() != null) {
            couponFlag = Integer.parseInt(arguments[0].get().toString());
        }

        double voucherPay = 0.0D;
        if(arguments[1].get() != null) {
            voucherPay = Double.parseDouble(arguments[1].get().toString());
        }

        double cashPay = 0.0D;
        if(arguments[2].get() != null) {
            cashPay = Double.parseDouble(arguments[2].get().toString());
        }

        if(couponFlag == 1) {
            ret = 1;
        } else if(cashPay > 0.0D) {
            ret = 2;
        } else if(voucherPay > 0.0D) {
            ret = 3;
        }

        return new IntWritable(ret);
    }

    public String getDisplayString(String[] children) {
        return "Usage: payment_parse(int couponFlag, double voucherPay, double cashPay)";
    }
}
