package org.llbqhh.test.disruptor.demo3;

/**
 * @Author lilibiao
 * @Date 2021/3/17
 * @Description: 具体处理逻辑
 */
import com.lmax.disruptor.WorkHandler;
import org.llbqhh.test.disruptor.demo1.LongEvent;

public class LongEventWorkHandler implements WorkHandler<LongEvent>
{
    @Override
    public void onEvent(LongEvent event) throws Exception {
        System.out.println("Event: " + event.getValue() + "," + event);
    }
}