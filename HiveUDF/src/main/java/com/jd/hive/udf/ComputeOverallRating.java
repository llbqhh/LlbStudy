package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class ComputeOverallRating extends GenericUDF {
    public ComputeOverallRating() {
    }

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length != 16) {
            throw new UDFArgumentLengthException("The function compute_overall_rating(arguments) takes exactly 16 arguments.");
        } else {
            return PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        assert arguments.length == 16;

        double serviceScore = (new Double(24.0D)).doubleValue();
        double productScore = (new Double(36.0D)).doubleValue();
        double serviceGrade = 3.0D;
        if(arguments[0].get() != null) {
            serviceGrade = Double.parseDouble(arguments[0].get().toString());
        }

        double productGrade = 3.0D;
        if(arguments[1].get() != null) {
            productGrade = Double.parseDouble(arguments[1].get().toString());
        }

        String userRecord = "";
        if(arguments[2].get() != null) {
            userRecord = arguments[2].get().toString();
        }

        String companyFlag = "";
        if(arguments[3].get() != null) {
            companyFlag = arguments[3].get().toString();
        }

        String complaints = "";
        if(arguments[4].get() != null) {
            complaints = arguments[4].get().toString();
        }

        int technicalOrderNum = 0;
        if(arguments[5].get() != null) {
            technicalOrderNum = Integer.parseInt(arguments[5].get().toString());
        }

        int nonTechnicalOrderNum = 0;
        if(arguments[6].get() != null) {
            nonTechnicalOrderNum = Integer.parseInt(arguments[6].get().toString());
        }

        int renewFeeNum = 0;
        if(arguments[7].get() != null) {
            renewFeeNum = Integer.parseInt(arguments[7].get().toString());
        }

        int upgradeConfNum = 0;
        if(arguments[8].get() != null) {
            upgradeConfNum = Integer.parseInt(arguments[8].get().toString());
        }

        int consumptionRanked = 0;
        if(arguments[9].get() != null) {
            consumptionRanked = Integer.parseInt(arguments[9].get().toString());
        }

        double accountBalance = 0.0D;
        if(arguments[10].get() != null) {
            accountBalance = Double.parseDouble(arguments[10].get().toString());
        }

        String expiryDate = "";
        if(arguments[11].get() != null) {
            expiryDate = arguments[11].get().toString();
        }

        int feeRanked = 0;
        if(arguments[12].get() != null) {
            feeRanked = Integer.parseInt(arguments[12].get().toString());
        }

        String withdrawals = "";
        if(arguments[13].get() != null) {
            withdrawals = arguments[13].get().toString();
        }

        String refund = "";
        if(arguments[14].get() != null) {
            refund = arguments[14].get().toString();
        }

        int faultNum = 0;
        if(arguments[15].get() != null) {
            faultNum = Integer.parseInt(arguments[15].get().toString());
        }

        if(serviceGrade >= 4.0D) {
            serviceScore += (serviceGrade * 10.0D - 39.0D) * 0.21818181818181817D;
        } else if(serviceGrade <= 2.0D) {
            serviceScore -= (21.0D - serviceGrade * 10.0D) * 0.0D;
        }

        if(userRecord.equals("备案")) {
            serviceScore += 2.4D;
        }

        if(companyFlag.equals("企业认证")) {
            serviceScore += 4.0D;
        }

        if(complaints.equals("投拆")) {
            serviceScore -= 12.0D;
        }

        if(nonTechnicalOrderNum > 4) {
            serviceScore -= 3.12D;
        }

        if(renewFeeNum < 3) {
            productScore += (double)renewFeeNum * 1.2D;
        } else if(renewFeeNum >= 3) {
            productScore += 3.6D;
        }

        if(upgradeConfNum < 3) {
            productScore += (double)upgradeConfNum * 0.8D;
        } else if(upgradeConfNum >= 3) {
            productScore += 2.4D;
        }

        if(consumptionRanked > 1000) {
            ++productScore;
        } else if(consumptionRanked <= 1000 && consumptionRanked > 500) {
            productScore += 2.4D;
        } else if(consumptionRanked <= 500 && consumptionRanked > 100) {
            productScore += 3.5999999999999996D;
        } else if(consumptionRanked <= 100) {
            productScore += 4.8D;
        }

        if(accountBalance < 100.0D) {
            productScore += 0.6D;
        } else if(accountBalance >= 100.0D && accountBalance < 800.0D) {
            ++productScore;
        } else if(accountBalance >= 800.0D && accountBalance < 8000.0D) {
            ++productScore;
        } else if(accountBalance >= 8000.0D) {
            productScore += 2.4D;
        }

        if(productGrade >= 4.0D) {
            productScore += (productGrade * 10.0D - 39.0D) * 0.32727272727272727D;
        } else if(productGrade <= 2.0D) {
            productScore -= (21.0D - productGrade * 10.0D) * 0.6545454545454545D;
        }

        if(expiryDate.equals("大于半年")) {
            productScore += 4.8D;
        }

        if(feeRanked > 1000) {
            productScore += 0.6D;
        } else if(feeRanked <= 1000 && feeRanked > 500) {
            ++productScore;
        } else if(feeRanked <= 500 && feeRanked > 100) {
            ++productScore;
        } else if(feeRanked <= 100) {
            productScore += 2.4D;
        }

        if(withdrawals.equals("提现")) {
            productScore -= 5.4D;
        }

        if(refund.equals("退款")) {
            productScore -= 9.0D;
        }

        if(faultNum < 6) {
            productScore -= (double)faultNum * 1.8D;
        } else if(faultNum > 6) {
            productScore -= 10.8D;
        }

        if(technicalOrderNum > 4) {
            productScore -= 3.6D;
        }

        return new DoubleWritable(serviceScore + productScore);
    }

    public String getDisplayString(String[] children) {
        return "Usage: compute_overall_rating(arguments)";
    }
}
