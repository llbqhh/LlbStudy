package org.llbqhh.test.disruptor.demo3;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.llbqhh.test.disruptor.demo1.LongEvent;
import org.llbqhh.test.disruptor.demo1.LongEventFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

/**
 * @Author lilibiao
 * @Date 2021/3/18
 * @Description:
 */
public class LongEventMainError
{
    public static void main(String[] args) throws Exception
    {
        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        // 改成2试试
        int bufferSize = 1024 * 1024;

        // Construct the Disruptor
        // 指定多线程方式处理
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        // Connect the handler
        disruptor.handleEventsWith((EventHandler) (event, sequence, endOfBatch) -> {
            return;
        });

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        int tNum = 10000;

        final CountDownLatch latch = new CountDownLatch(tNum);
        long startTime = System.currentTimeMillis();
        for(int i=0; i<tNum; i++){
            final int index = i;
            //开启多个线程发布事件。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long sequence = ringBuffer.next();
                    try {
                        ByteBuffer bb = ByteBuffer.allocate(8);
                        bb.putLong(0, index);
                        LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
                        event.set(bb.getLong(0));  // Fill with data
                    } finally{
                        ringBuffer.publish(sequence);
                    }
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
            for(int i=0;i<tNum;i++){
                LongEvent event = ringBuffer.get(i);
                System.out.println(event.getValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}