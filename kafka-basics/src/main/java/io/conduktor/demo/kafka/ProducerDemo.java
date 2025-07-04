package io.conduktor.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProducerDemo.class);
    public static void main(String[] args) {
        LOGGER.info("I ama starting the producer");

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>("notification-email-queue", "Hello World!");
        producer.send(record);
        producer.flush();
        producer.close();
    }
}
