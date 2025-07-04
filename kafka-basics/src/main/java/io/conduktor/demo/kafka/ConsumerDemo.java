package io.conduktor.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConsumerDemo {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ConsumerDemo.class);
    public static void main(String[] args) {
        LOGGER.info("I ama starting the consumer");


        String topic = "notification-email-queue";
        String groupId = "test-group";
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");


        //consumer config
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", StringDeserializer.class.getName());
        props.setProperty("group.id", groupId);
        props.setProperty("auto.offset.reset", "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        //subscribe topic
        consumer.subscribe(List.of(topic));

        while(true){
            LOGGER.info("polling records");
           ConsumerRecords<String,String> records= consumer.poll(1000);
            for(ConsumerRecord<String,String> rec:records){
                LOGGER.info("Key : {} \nValue : {}", rec.key(), rec.value());
                LOGGER.info("Partition : {} \nOffset : {}", rec.partition(), rec.offset());
            }
        }
    }
}
