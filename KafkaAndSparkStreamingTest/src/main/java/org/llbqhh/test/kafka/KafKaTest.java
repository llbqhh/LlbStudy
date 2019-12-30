package org.llbqhh.test.kafka;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by lilibiao on 2017/11/1.
 */
public class KafKaTest {
    private static KafkaProducer<String, byte[]> producer = null;

    private static String kafkaHost = "localhost";

    static {
        //生产者配置文件，具体配置可参考ProducerConfig类源码，或者参考官网介绍
        Map<String, Object> config = new HashMap<String, Object>();
        //kafka服务器地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":9092");
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.100.90:9092,192.168.100.91:9092");
        //kafka消息序列化类 即将传入对象序列化为字节数组
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        //kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024 * 1024 * 5);
        //往kafka服务器提交消息间隔时间，0则立即提交不等待
        config.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        //消费者配置文件
        Properties props = new Properties();
        producer = new KafkaProducer<String, byte[]>(config);
    }

    /**
     * 发送消息，发送的对象必须是可序列化的
     */
    public static Future<RecordMetadata> send(String topic, Serializable value) throws Exception {
        try {
            //将对象序列化称字节码
            byte[] bytes = SerializationUtils.serialize(value);
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, byte[]>(topic, bytes));
            return future;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Future<RecordMetadata> sendStr(String topic, String value) throws Exception {
        try {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, byte[]>(topic, value.getBytes()));
            return future;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void receive() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaHost + ":9092");
        props.put("group.id", "test receive 1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("mystrings", "myusers"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
        }
    }

    public static void main(String[] args) throws Exception {
        //发送一个信息,可以发送可序列化的对象，当然也可以直接发送字符串
        for (int i = 0; i < 1000; i++) {
            send("myusers", new User("id" + i, "userName" + i, "password" + i));
            sendStr("mystrings", i + "");
        }
        TimeUnit.SECONDS.sleep(3);
        receive();
    }
}

class User implements Serializable {
    private String id;
    private String userName;
    private String password;

    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("user's id is [%s], userName is [%s], password is [%s]", id, userName, password);
    }
}
