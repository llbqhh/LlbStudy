package org.llbqhh.test.disruptor.demo1;

/**
 * @Author lilibiao
 * @Date 2021/3/17
 * @Description: 具体处理逻辑
 */
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event.getValue() + "," + event);
    }
}