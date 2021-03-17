package org.llbqhh.test.disruptor.ability;

/**
 * @Author lilibiao
 * @Date 2021/3/16
 * @Description:
 */
import com.lmax.disruptor.EventHandler;

public class DataConsumer implements EventHandler<Data> {

    private long startTime;
    private int i;

    public DataConsumer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(Data data, long seq, boolean bool)
            throws Exception {
        i++;
        if (i == Constants.EVENT_NUM_OHM) {
            long endTime = System.currentTimeMillis();
            System.out.println("Disruptor costTime = " + (endTime - startTime) + "ms");
        }
    }

}