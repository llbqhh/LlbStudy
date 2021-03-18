package org.llbqhh.test.disruptor.demo1;

/**
 * @Author lilibiao
 * @Date 2021/3/17
 * @Description: 定义事件
 */
public class LongEvent
{
    private long value = -1;

    public void set(long value)
    {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    public void clear()
    {
        this.value = -1;
        System.out.println("clear..." + this.value);
    }
}