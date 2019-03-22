package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class FormulaParse extends GenericUDF {
    public static final Gson gson = new Gson();
    private final Text text = new Text();

    public FormulaParse() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 3) {
            throw new UDFArgumentLengthException("The function formula_parse(jsonStr) takes exactly 3 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        this.text.clear();

        assert arguments.length == 3;

        if(arguments[0].get() != null && arguments[1].get() != null && arguments[2].get() != null) {
            String serviceCode = arguments[0].get().toString();
            String networkOperator = arguments[1].get().toString();
            String jsonStr = arguments[2].get().toString();
            if(jsonStr != null && jsonStr.trim().length() != 0) {
                if(serviceCode.equals("ip") && (networkOperator == null || networkOperator.trim().length() == 0)) {
                    return null;
                } else {
                    try {
                        HashMap jsonToMap = new HashMap();
                        Map jsonToMap1 = (Map)gson.fromJson(jsonStr, jsonToMap.getClass());
                        if(!serviceCode.equals("ip")) {
                            StringBuilder sb1 = new StringBuilder("");
                            double value = 0.0D;
                            Iterator var9 = jsonToMap1.entrySet().iterator();

                            while(var9.hasNext()) {
                                Entry entry2 = (Entry)var9.next();
                                String key = (String)entry2.getKey();
                                if(!key.equals("timeUnit") && !((Double)entry2.getValue()).isNaN()) {
                                    value = ((Double)entry2.getValue()).doubleValue();
                                    sb1.append(key + ":" + Double.toString(value) + ", ");
                                }
                            }

                            if(sb1.toString().length() > 2) {
                                this.text.append(sb1.toString().getBytes(), 0, sb1.toString().length() - 2);
                            } else {
                                this.text.append(sb1.toString().getBytes(), 0, sb1.toString().length());
                            }

                            return this.text;
                        } else {
                            double sb = 0.0D;
                            double ipbw = 0.0D;
                            double entry = 0.0D;
                            double ipcf = 0.0D;
                            double ipvo = 0.0D;
                            Iterator str = jsonToMap1.entrySet().iterator();

                            while(true) {
                                while(str.hasNext()) {
                                    Entry entry1 = (Entry)str.next();
                                    String key1 = (String)entry1.getKey();
                                    if(key1.equals("bd")) {
                                        sb = ((Double)entry1.getValue()).doubleValue();
                                    } else if(key1.equals("bw") && !((Double)entry1.getValue()).isNaN()) {
                                        ipbw = ((Double)entry1.getValue()).doubleValue();
                                    } else if(key1.equals("rt") && !((Double)entry1.getValue()).isNaN()) {
                                        entry = ((Double)entry1.getValue()).doubleValue();
                                    } else if(key1.equals("cf") && !((Double)entry1.getValue()).isNaN()) {
                                        ipcf = ((Double)entry1.getValue()).doubleValue();
                                    } else if(key1.equals("vo") && !((Double)entry1.getValue()).isNaN()) {
                                        ipvo = ((Double)entry1.getValue()).doubleValue();
                                    }
                                }

                                if(networkOperator.equals("1")) {
                                    serviceCode = "ip单线";
                                } else if(networkOperator.equals("2")) {
                                    serviceCode = "ipBGP";
                                } else if(networkOperator.equals("3")) {
                                    serviceCode = "ip爬虫单线";
                                } else if(networkOperator.equals("4")) {
                                    serviceCode = "ip爬虫专享";
                                } else {
                                    serviceCode = "ip";
                                }

                                String str1 = serviceCode + ":" + Double.toString(sb + ipbw + entry + ipcf + ipvo) + " Mbps";
                                this.text.append(str1.getBytes(), 0, str1.getBytes("utf-8").length);
                                return this.text;
                            }
                        }
                    } catch (Exception var19) {
                        return null;
                    }
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getDisplayString(String[] children) {
        return "Usage: formula_parse(String serviceCode, String networkOperator, String jsonStr)";
    }
}
