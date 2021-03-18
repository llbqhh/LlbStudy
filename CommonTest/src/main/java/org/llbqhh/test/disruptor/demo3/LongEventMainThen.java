package org.llbqhh.test.disruptor.demo3;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.llbqhh.test.disruptor.demo1.LongEvent;
import org.llbqhh.test.disruptor.demo1.LongEventFactory;
import org.llbqhh.test.disruptor.demo1.LongEventHandler;
import org.llbqhh.test.disruptor.demo1.LongEventProducer;

import java.nio.ByteBuffer;

/**
 * @Author lilibiao
 * @Date 2021/3/18
 * @Description:
 */

class ClearingEventHandler<T> implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        // Failing to call clear here will result in the
        // object associated with the event to live until
        // it is overwritten once the ring buffer has wrapped
        // around to the beginning.
        event.clear();
    }
}

public class LongEventMainThen {
    public static void main(String[] args) throws InterruptedException {

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        // 指定多线程方式处理
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());

        // Connect the handler
        disruptor.handleEventsWith(new LongEventHandler())
                .and(new BatchEventProcessor<>(disruptor.getRingBuffer(),disruptor.getRingBuffer().newBarrier(), new LongEventHandler()))
                .then(new ClearingEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
//            System.out.println(ringBuffer.get(l).getValue());
        }
    }
}