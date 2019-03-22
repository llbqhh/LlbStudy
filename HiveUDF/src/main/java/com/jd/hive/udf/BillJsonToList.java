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

public class BillJsonToList extends UDF {
    public static final Gson gson = new Gson();

    public BillJsonToList() {
    }

    public String evaluate(String serviceCode, String networkOperator, String jsonStr) {
        if(jsonStr != null && jsonStr.trim().length() != 0) {
            String vmc = "0";
            String vmm = "0";
            double ipbd = 0.0D;
            double ipbw = 0.0D;
            String ipcf = "0";
            String ipvo = "0";
            String iprt = "0";
            double bgpipbd = 0.0D;
            double bgpipbw = 0.0D;
            String bgpipcf = "0";
            String bgpipvo = "0";
            String bgpiprt = "0";
            String diskc = "0";
            String diskcs = "0";
            String storagelrvo = "0";
            String storagevo = "0";
            String storaget = "0";
            String databasec = "0";
            String databasem = "0";
            String databased = "0";
            String redism = "0";
            String redismcluster = "0";
            HashMap map = new HashMap();
            Map map1 = (Map)gson.fromJson(jsonStr, map.getClass());
            Iterator var31 = map1.entrySet().iterator();

            while(true) {
                while(var31.hasNext()) {
                    Entry entry = (Entry)var31.next();
                    String key = (String)entry.getKey();
                    if(serviceCode.equals("vm") && key.equals("c") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        vmc = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("vm") && key.equals("m") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        vmm = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && key.equals("bd") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        ipbd = ((Double)entry.getValue()).doubleValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && key.equals("bw") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        ipbw = ((Double)entry.getValue()).doubleValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && key.equals("cf") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        ipcf = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && key.equals("vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        ipvo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && key.equals("rt") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        iprt = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && key.equals("bd") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        bgpipbd = ((Double)entry.getValue()).doubleValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && key.equals("bw") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        bgpipbw = ((Double)entry.getValue()).doubleValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && key.equals("cf") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        bgpipcf = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && key.equals("vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        bgpipvo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && key.equals("rt") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        bgpiprt = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("disk") && key.equals("c") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        diskc = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("disk") && key.equals("cs") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        diskcs = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("storage") && key.equals("lr_vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storagelrvo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("storage") && key.equals("vo") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storagevo = String.valueOf(entry.getValue());
                    } else if(serviceCode.equals("storage") && key.equals("t") && ((Double)entry.getValue()).doubleValue() != 0.0D) {
                        storaget = String.valueOf(entry.getValue());
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

                return vmc + "," + vmm + "," + (ipbd + ipbw) + "," + ipcf + "," + ipvo + "," + iprt + "," + (bgpipbd + bgpipbw) + "," + bgpipcf + "," + bgpipvo + "," + bgpiprt + "," + diskc + "," + diskcs + "," + storagelrvo + "," + storagevo + "," + storaget + "," + databasec + "," + databasem + "," + databased + "," + redism + "," + redismcluster;
            }
        } else {
            return null;
        }
    }
}
