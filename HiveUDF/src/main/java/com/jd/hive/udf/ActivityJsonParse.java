package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.hive.udf.model.ActivityIDModel;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class ActivityJsonParse extends GenericUDF {
    public static final Gson gson = new Gson();
    private final Text text = new Text();

    public ActivityJsonParse() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 1) {
            throw new UDFArgumentLengthException("The function activity_json_parse(jsonArrayStr) takes exactly 1 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        this.text.clear();

        assert arguments.length == 1;

        if(arguments[0].get() == null) {
            return null;
        } else {
            String jsonArrayStr = arguments[0].get().toString();
            if(StringUtils.isNotEmpty(jsonArrayStr)) {
                try {
                    ArrayList lists = (ArrayList)gson.fromJson(jsonArrayStr, (new TypeToken() {
                    }).getType());
                    if(!lists.isEmpty()) {
                        Iterator var4 = lists.iterator();

                        while(var4.hasNext()) {
                            ActivityIDModel activityIDModel = (ActivityIDModel)var4.next();
                            String str = activityIDModel.getActivityCode();
                            this.text.append(str.getBytes(), 0, str.length());
                        }

                        return this.text;
                    }
                } catch (Exception var7) {
                    ;
                }
            }

            return null;
        }
    }

    public String getDisplayString(String[] children) {
        return "Usage: activity_json_parse(String jsonArrayStr)";
    }
}