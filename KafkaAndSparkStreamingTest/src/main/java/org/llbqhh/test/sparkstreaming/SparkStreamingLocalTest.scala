package org.llbqhh.test.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by lilibiao on 2017/11/1.
  */
object SparkStreamingLocalTest {

  def main(args: Array[String]): Unit = {

    //      val conf = new SparkConf().setMaster("local[2]")
    //      val conf = new SparkConf().setMaster("spark://llb-test-linux:7077")
    val conf = new SparkConf().setMaster("yarn")
    conf.setAppName("SparkStreamTest").set("spark.executor.memory", "450m")
    //每隔5秒计算一批数据local[2]：意思本地起2个进程运行，setAppName("SparkStreaming")：设置运行处理类
    val ssc = new StreamingContext(conf, Seconds(5))
    // 指定监控的目录
    val lines = ssc.textFileStream("/tmp/streaming/logs/")
    //按\t 切分输入数据
    val words = lines.flatMap(_.split("\t"))
    //计算wordcount
    val pairs = words.map(word => (word, 1))
    //word ++
    val wordCounts = pairs.reduceByKey(_ + _)
    //排序结果集打印，先转成rdd，然后排序true升序，false降序，可以指定key和value排序_._1是key，_._2是value
    val sortResult = wordCounts.transform(rdd => rdd.sortBy(_._2, false))
    sortResult.print()
    ssc.start() // 开启计算
    ssc.awaitTermination() // 阻塞等待计算


  }
}
