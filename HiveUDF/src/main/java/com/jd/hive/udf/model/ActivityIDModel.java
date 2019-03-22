package com.jd.hive.udf.model;

/**
 * Created by lilibiao on 2018/4/20.
 */
public class ActivityIDModel {
    private String promotionType;
    private String activityCode;

    public ActivityIDModel() {
    }

    public String getPromotionType() {
        return this.promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getActivityCode() {
        return this.activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
}
