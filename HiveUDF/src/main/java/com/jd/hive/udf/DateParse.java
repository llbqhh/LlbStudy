package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.jd.hive.udf.util.DateUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.AbstractPrimitiveJavaObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class DateParse extends GenericUDF {
    private static String init = "";
    private transient ArrayList<String> ret = new ArrayList();

    public DateParse() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        System.out.println(new Date() + "########   initialize");
        init = "init";
        AbstractPrimitiveJavaObjectInspector returnOI = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING);
        return ObjectInspectorFactory.getStandardListObjectInspector(returnOI);
    }

    public Object evaluate(DeferredObject[] args) throws HiveException {
        this.ret.clear();
        if(args.length < 2) {
            return this.ret;
        } else {
            String beginDateStr = args[0].get().toString();
            String endDateStr = args[1].get().toString();

            try {
                this.ret = DateUtils.dateToList(beginDateStr, endDateStr);
            } catch (ParseException var5) {
                var5.printStackTrace();
            }

            return this.ret;
        }
    }

    public String getDisplayString(String[] children) {
        return "Usage: date_parse(String beginDateStr, String endDateStr)";
    }
}
