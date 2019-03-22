package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.hadoop.hive.ql.exec.UDF;

public class ResourceJsonToList extends UDF {
    public static final Gson gson = new Gson();

    public ResourceJsonToList() {
    }

    public String evaluate(String serviceCode, String jsonStr) {
        if(jsonStr != null && jsonStr.trim().length() != 0) {
            String vmc = "0";
            String vmm = "0";
            String diskc = "0";
            String diskcs = "0";
            String databasec = "0";
            String databasem = "0";
            String databased = "0";
            String redism = "0";
            String redismcluster = "0";
            HashMap map = new HashMap();
            Map map1 = (Map)gson.fromJson(jsonStr, map.getClass());
            Iterator var13 = map1.entrySet().iterator();

            while(true) {
                while(var13.hasNext()) {
                    Entry entry = (Entry)var13.next();
                    String key = (String)entry.getKey();
                    if(serviceCode.equals("vm") && key.equals("c") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        vmc = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("vm") && key.equals("m") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        vmm = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("disk") && key.equals("c") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        diskc = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("disk") && key.equals("cs") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        diskcs = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("database") && key.equals("c") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        databasec = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("database") && key.equals("m") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        databasem = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("database") && key.equals("d") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        databased = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("redis") && key.equals("m") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        redism = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("redis") && key.equals("m_cluster") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        redismcluster = String.valueOf(entry.getValue());
                    }
                }

                return vmc + "," + vmm + "," + diskc + "," + diskcs + "," + databasec + "," + databasem + "," + databased + "," + redism + "," + redismcluster;
            }
        } else {
            return null;
        }
    }
}
