package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.hive.udf.model.BasicModel;
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.hive.ql.exec.UDF;

public class ItemJsonToList extends UDF {
    public static final Gson gson = new Gson();

    public ItemJsonToList() {
    }

    public String evaluate(String serviceCode, String networkOperator, String jsonStr) {
        if(jsonStr != null && jsonStr.trim().length() != 0) {
            String vmc = "0";
            String vmm = "0";
            double ipbd = 0.0D;
            double ipbw = 0.0D;
            double iprt = 0.0D;
            double bgpipbd = 0.0D;
            double bgpipbw = 0.0D;
            double bgpiprt = 0.0D;
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
            List cmBasicModelList = (List)gson.fromJson(jsonStr, (new TypeToken() {
            }).getType());
            Iterator var29 = cmBasicModelList.iterator();

            while(true) {
                while(var29.hasNext()) {
                    BasicModel cmBasicModel = (BasicModel)var29.next();
                    if(serviceCode.equals("vm") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("c") && cmBasicModel.getValue() != 0.0D) {
                        vmc = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("vm") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("m") && cmBasicModel.getValue() != 0.0D) {
                        vmm = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("bd") && cmBasicModel.getValue() != 0.0D) {
                        ipbd = cmBasicModel.getValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("bw") && cmBasicModel.getValue() != 0.0D) {
                        ipbw = cmBasicModel.getValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("1") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("rt") && cmBasicModel.getValue() != 0.0D) {
                        iprt = cmBasicModel.getValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("bd") && cmBasicModel.getValue() != 0.0D) {
                        bgpipbd = cmBasicModel.getValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("bw") && cmBasicModel.getValue() != 0.0D) {
                        bgpipbw = cmBasicModel.getValue();
                    } else if(serviceCode.equals("ip") && networkOperator.equals("2") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("rt") && cmBasicModel.getValue() != 0.0D) {
                        bgpiprt = cmBasicModel.getValue();
                    } else if(serviceCode.equals("disk") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("c") && cmBasicModel.getValue() != 0.0D) {
                        diskc = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("disk") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("cs") && cmBasicModel.getValue() != 0.0D) {
                        diskcs = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("storage") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("lr_vo") && cmBasicModel.getValue() != 0.0D) {
                        storagelrvo = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("storage") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("vo") && cmBasicModel.getValue() != 0.0D) {
                        storagevo = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("storage") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("t") && cmBasicModel.getValue() != 0.0D) {
                        storaget = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("database") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("c") && cmBasicModel.getValue() != 0.0D) {
                        databasec = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("database") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("m") && cmBasicModel.getValue() != 0.0D) {
                        databasem = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("database") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("d") && cmBasicModel.getValue() != 0.0D) {
                        databased = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("redis") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("m") && cmBasicModel.getValue() != 0.0D) {
                        redism = String.valueOf(cmBasicModel.getValue());
                    } else if(serviceCode.equals("redis") && cmBasicModel.getKey() != null && cmBasicModel.getKey().equals("m_cluster") && cmBasicModel.getValue() != 0.0D) {
                        redismcluster = String.valueOf(cmBasicModel.getValue());
                    }
                }

                return vmc + "," + vmm + "," + (ipbd + ipbw + iprt) + "," + (bgpipbd + bgpipbw + bgpiprt) + "," + diskc + "," + diskcs + "," + storagelrvo + "," + storagevo + "," + storaget + "," + databasec + "," + databasem + "," + databased + "," + redism + "," + redismcluster;
            }
        } else {
            return null;
        }
    }
}
