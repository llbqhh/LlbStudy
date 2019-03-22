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

public class CsJsonToList extends UDF {
    public static final Gson gson = new Gson();

    public CsJsonToList() {
    }

    public String evaluate(String serviceCode, String jsonStr) {
        if(jsonStr != null && jsonStr.trim().length() != 0) {
            String storagelrvo = "0";
            String storagevo = "0";
            String storaget = "0";
            String cdnt = "0";
            String cdnt1 = "0";
            String cdnt2 = "0";
            String cdnp = "0";
            HashMap map = new HashMap();
            Map map1 = (Map)gson.fromJson(jsonStr, map.getClass());
            Iterator var11 = map1.entrySet().iterator();

            while(true) {
                while(var11.hasNext()) {
                    Entry entry = (Entry)var11.next();
                    String key = (String)entry.getKey();
                    if(serviceCode.equals("storage") && key.equals("lr_vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storagelrvo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("storage") && key.equals("vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storagevo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("storage") && key.equals("t") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storaget = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("cdn") && key.equals("t") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        cdnt = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("cdn") && key.equals("t.1") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        cdnt1 = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("cdn") && key.equals("t.2") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        cdnt2 = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("cdn") && key.equals("p") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        cdnp = String.valueOf(entry.getValue());
                    }
                }

                return storagelrvo + "," + storagevo + "," + storaget + "," + cdnt + "," + cdnt1 + "," + cdnt2 + "," + cdnp;
            }
        } else {
            return null;
        }
    }
}
