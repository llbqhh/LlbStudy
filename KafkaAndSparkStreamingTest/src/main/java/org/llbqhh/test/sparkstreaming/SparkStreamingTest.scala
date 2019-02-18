package org.llbqhh.test.sparkstreaming

import org.apache.hadoop.security.UserGroupInformation
import org.scalatest.tools.Durations
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**
  * Created by lilibiao on 2017/11/1.
  */
object SparkStreamingTest {
  def main(args: Array[String]): Unit = {


    //    val ugi = UserGroupInformation.createRemoteUser("hadoop");
    //    UserGroupInformation.setLoginUser(ugi)

    //      val conf = new SparkConf().setMaster("yarn")
    val conf = new SparkConf().setMaster("spark://llb-test-linux:7077")
    //  val conf = new SparkConf().setMaster("local[2]")
    conf.setAppName("KafkaStreamTest").set("spark.executor.memory", "471859200")
    val ssc = new StreamingContext(conf, Seconds(3))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "llb-test-linux:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "llb's test app",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("mystrings", "myusers")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    //关于record可以看这篇博客https://www.w3cschool.cn/apache_kafka/apache_kafka_simple_producer_example.html
    //在博客中搜索一下ConsumerRecord
    val lines = stream.map(record => record.value);

    //按\t 切分输入数据
    val words = lines.flatMap(_.split("\t"))
    //计算wordcount
    val pairs = words.map(word => (word, 1))
    //word ++
    val wordCounts = pairs.reduceByKey(_ + _)
    //排序结果集打印，先转成rdd，然后排序true升序，false降序，可以指定key和value排序_._1是key，_._2是value
    val sortResult = wordCounts.transform(rdd => rdd.sortBy(_._2, false))

    sortResult.print(20)

    ssc.start() // 开启计算
    ssc.awaitTermination() // 阻塞等待计算
  }


}