package org.llbqhh.study.threadpoll;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MyFixedSizeThreadPool {
    private BlockingDeque<Runnable> tasks;
    private List<Worker> workers;
    private volatile boolean working = true;

    public MyFixedSizeThreadPool(int queueSize, int workerNum) {
        if (queueSize < 0 || workerNum < 0) {
            throw new IllegalArgumentException("参数错误");
        }
        tasks = new LinkedBlockingDeque<>(queueSize);
        workers = new ArrayList<>(workerNum);
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker(this);
            workers.add(worker);
            worker.start();
        }
    }

    public boolean submit(Runnable task) {
        if (this.working) {
            return this.tasks.offer(task);
        }
        return false;
    }

    public void shutdown() {
        this.working = false;
        for (Thread t : workers) {
            if (t.getState().equals(Thread.State.BLOCKED)
                    || t.getState().equals(Thread.State.WAITING)
                    || t.getState().equals(Thread.State.TIMED_WAITING)) {
                t.interrupt();
            }
        }
    }

    private static class Worker extends Thread {
        private MyFixedSizeThreadPool pool;

        public Worker(MyFixedSizeThreadPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while (this.pool.working || !this.pool.tasks.isEmpty()) {
                Runnable task = null;
                try {
                    if (this.pool.working) {
                        task = this.pool.tasks.take();
                    } else {
                        task = this.pool.tasks.poll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (task != null) {
                    task.run();
                    System.out.println(Thread.currentThread().getName() + ":完成任务...");
                }
            }
            System.out.println(Thread.currentThread().getName() + ":线程退出...");
        }
    }

    public static void main(String[] args) {
        MyFixedSizeThreadPool pool = new MyFixedSizeThreadPool(6, 3);
        for (int i = 0; i < 6; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread start...");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        pool.shutdown();
    }
}