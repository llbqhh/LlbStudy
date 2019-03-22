package com.jd.hive.udf.model;

/**
 * Created by lilibiao on 2018/4/20.
 */
public class BasicModel {
    private String key;
    private String unit;
    private double value;

    public BasicModel() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = (double)value;
    }
}
