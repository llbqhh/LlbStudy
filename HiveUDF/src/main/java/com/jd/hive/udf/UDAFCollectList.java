package com.jd.hive.udf;

/**
 * Created by lilibiao on 2018/4/20.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.AggregationBuffer;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StandardListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

@Description(
        name = "collect_list_frequency",
        value = "_FUNC_(x) - Returns a list of objects with count of duplicate elements"
)
public class UDAFCollectList extends AbstractGenericUDAFResolver {
    static final Log LOG = LogFactory.getLog(UDAFCollectList.class.getName());

    public UDAFCollectList() {
    }

    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters) throws SemanticException {
        if(parameters.length != 1) {
            throw new UDFArgumentTypeException(parameters.length - 1, "Exactly one argument is expected.");
        } else if(parameters[0].getCategory() != Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but " + parameters[0].getTypeName() + " was passed as parameter 1.");
        } else {
            return new UDAFCollectList.GenericUDAFMkSetEvaluator();
        }
    }

    public static class GenericUDAFMkSetEvaluator extends GenericUDAFEvaluator {
        private PrimitiveObjectInspector inputOI;
        private StandardListObjectInspector loi;
        private StandardListObjectInspector internalMergeOI;

        public GenericUDAFMkSetEvaluator() {
        }

        public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            if(m == Mode.PARTIAL1) {
                this.inputOI = (PrimitiveObjectInspector)parameters[0];
                return ObjectInspectorFactory.getStandardListObjectInspector((PrimitiveObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(this.inputOI));
            } else if(!(parameters[0] instanceof StandardListObjectInspector)) {
                this.inputOI = (PrimitiveObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(parameters[0]);
                return ObjectInspectorFactory.getStandardListObjectInspector(this.inputOI);
            } else {
                this.internalMergeOI = (StandardListObjectInspector)parameters[0];
                this.inputOI = (PrimitiveObjectInspector)this.internalMergeOI.getListElementObjectInspector();
                this.loi = (StandardListObjectInspector)ObjectInspectorUtils.getStandardObjectInspector(this.internalMergeOI);
                return this.loi;
            }
        }

        public void reset(AggregationBuffer agg) throws HiveException {
            ((UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer)agg).container = new ArrayList();
        }

        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer ret = new UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer();
            this.reset(ret);
            return ret;
        }

        public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
            assert parameters.length == 1;

            Object p = parameters[0];
            if(p != null) {
                UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer myagg = (UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer)agg;
                this.putIntoList(p, myagg);
            }

        }

        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer myagg = (UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer)agg;
            ArrayList ret = new ArrayList(myagg.container.size());
            ret.addAll(myagg.container);
            return ret;
        }

        public void merge(AggregationBuffer agg, Object partial) throws HiveException {
            UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer myagg = (UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer)agg;
            ArrayList partialResult = (ArrayList)this.internalMergeOI.getList(partial);
            Iterator var5 = partialResult.iterator();

            while(var5.hasNext()) {
                Object i = var5.next();
                this.putIntoList(i, myagg);
            }

        }

        public Object terminate(AggregationBuffer agg) throws HiveException {
            UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer myagg = (UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer)agg;
            ArrayList ret = new ArrayList(myagg.container.size());
            ret.addAll(myagg.container);
            return ret;
        }

        private void putIntoList(Object p, UDAFCollectList.GenericUDAFMkSetEvaluator.MkArrayAggregationBuffer myagg) {
            Object pCopy = ObjectInspectorUtils.copyToStandardObject(p, this.inputOI);
            myagg.container.add(pCopy);
        }

        static class MkArrayAggregationBuffer implements AggregationBuffer {
            List<Object> container;

            MkArrayAggregationBuffer() {
            }
        }
    }
}
