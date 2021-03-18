package org.llbqhh.test.disruptor.demo3;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.llbqhh.test.disruptor.demo1.LongEvent;
import org.llbqhh.test.disruptor.demo1.LongEventFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author lilibiao
 * @Date 2021/3/18
 * @Description:
 */
public class LongEventMainWorkPool
{
    public static void main(String[] args) throws Exception
    {
        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        // 指定多线程方式处理
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());

        // Connect the handler
//        disruptor.handleEventsWith((EventHandler) (event, sequence, endOfBatch) -> {
//            return;
//        });

        LongEventWorkHandler handler1 = new LongEventWorkHandler();
        LongEventWorkHandler handler2 = new LongEventWorkHandler();
        LongEventWorkHandler handler3 = new LongEventWorkHandler();
        WorkerPool<LongEvent> workerPool =
                new WorkerPool<LongEvent>(disruptor.getRingBuffer(),
                        disruptor.getRingBuffer().newBarrier(),
                        new IgnoreExceptionHandler(),
                        handler1,handler2,handler3);

        //创建一个线程池用于执行Workhandler。
        Executor executor = Executors.newFixedThreadPool(4);
        //启动WorkPool。
        workerPool.start(executor);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        int tNum = 10;

        final CountDownLatch latch = new CountDownLatch(tNum);
        long startTime = System.currentTimeMillis();
        for(int i=0; i<tNum; i++){
            final int index = i;
            //开启多个线程发布事件。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long startTime = System.currentTimeMillis();
                    for (int i = 0; i< 1000; i++) {
                        long sequence = ringBuffer.next();
                        try {
                            ByteBuffer bb = ByteBuffer.allocate(8);
                            bb.putLong(0, index);
                            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
                            event.set(bb.getLong(0));  // Fill with data
                        } finally{
                            ringBuffer.publish(sequence);
                        }
                    }
                    latch.countDown();
                    System.out.println(Thread.currentThread().getName() + "-" + (System.currentTimeMillis() - startTime));
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}