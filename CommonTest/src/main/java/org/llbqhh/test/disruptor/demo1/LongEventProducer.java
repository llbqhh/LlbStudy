package org.llbqhh.test.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author lilibiao
 * @Date 2021/3/18
 * @Description: 原生api方式发布事件
 */
public class LongEventProducer
{
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.set(bb.getLong(0));  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}