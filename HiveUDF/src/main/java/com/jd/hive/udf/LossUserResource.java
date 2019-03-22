package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.apache.hadoop.hive.ql.exec.UDF;

public class LossUserResource extends UDF {
    public static final Gson gson = new Gson();

    public LossUserResource() {
    }

    public String evaluate(String serviceCode, String networkOperator, String jsonStr) {
        if(jsonStr != null && jsonStr.trim().length() != 0) {
            double ipbd = 0.0D;
            double ipbw = 0.0D;
            double iprt = 0.0D;
            HashMap jsonToMap = new HashMap();
            Map jsonToMap1 = (Map)gson.fromJson(jsonStr, jsonToMap.getClass());
            HashMap newMap = new HashMap();
            String mapToJson;
            Iterator treeMap;
            Entry entry;
            String key;
            if(serviceCode.equals("ip")) {
                treeMap = jsonToMap1.entrySet().iterator();

                while(treeMap.hasNext()) {
                    entry = (Entry)treeMap.next();
                    key = (String)entry.getKey();
                    if(key.equals("bd")) {
                        ipbd = ((Double)entry.getValue()).doubleValue();
                    } else if(key.equals("bw")) {
                        ipbw = ((Double)entry.getValue()).doubleValue();
                    } else if(key.equals("rt")) {
                        iprt = ((Double)entry.getValue()).doubleValue();
                    }
                }

                if(networkOperator.equals("1")) {
                    serviceCode = "ip单线";
                }

                if(networkOperator.equals("2")) {
                    serviceCode = "ipBGP";
                }

                if(networkOperator.equals("3")) {
                    serviceCode = "ip爬虫单线";
                }

                if(networkOperator.equals("4")) {
                    serviceCode = "ip爬虫专享";
                }

                newMap.put("bw", Double.valueOf(ipbd + ipbw + iprt));
                mapToJson = gson.toJson(newMap);
            } else {
                treeMap = jsonToMap1.entrySet().iterator();

                while(treeMap.hasNext()) {
                    entry = (Entry)treeMap.next();
                    key = (String)entry.getKey();
                    if(!key.equals("timeUnit")) {
                        newMap.put(key, entry.getValue());
                    }
                }

                TreeMap treeMap1 = new TreeMap(newMap);
                mapToJson = gson.toJson(treeMap1);
            }

            return serviceCode + "-" + mapToJson;
        } else {
            return null;
        }
    }

    public String evaluate(String resourceStr, String num) {
        return resourceStr != null && resourceStr.trim().length() != 0?resourceStr + "*" + num:null;
    }
}
