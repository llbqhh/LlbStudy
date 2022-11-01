package org.llbqhh.test.disruptor.demo1;

/**
 * @Author lilibiao
 * @Date 2021/3/17
 * @Description: 工厂类用来预分配内存对象
 */
import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}