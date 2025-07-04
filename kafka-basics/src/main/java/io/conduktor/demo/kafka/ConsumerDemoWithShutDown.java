package io.conduktor.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

public class ConsumerDemoWithShutDown {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ConsumerDemoWithShutDown.class);
    public static void main(String[] args) {
        LOGGER.info("I ama starting the consumer with shutdown");


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


        //create shutdown hook
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
           public void run(){
                LOGGER.info("detected shutdown ");
                consumer.wakeup();
                //join the main thread to allow the execution of the code in the main thread to complete

                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            //subscribe topic
            consumer.subscribe(List.of(topic));

            while (true) {
                LOGGER.info("polling records");
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> rec : records) {
                    LOGGER.info("Key : {} \nValue : {}", rec.key(), rec.value());
                    LOGGER.info("Partition : {} \nOffset : {}", rec.partition(), rec.offset());
                }
            }
        }catch (WakeupException e){
            LOGGER.info("consumer is shutting down");
        }catch (Exception e){
            LOGGER.error("Unexpected exception in the consumer");
        }finally {
            consumer.close();
            LOGGER.info("consumer is closed gracefully");
        }
    }
}
