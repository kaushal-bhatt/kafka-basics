package io.conduktor.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import java.util.Properties;

public class ProducerDemoWIthCallbackKeys {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProducerDemoWIthCallbackKeys.class);
    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("I am starting the producer");

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());
        props.setProperty("batch.size", "400");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int j=0;j<=10;j++){
            for(int i=0;i<=30;i++) {

                String topic= "notification-email-queue";
                String key= "id_"+i;
                String value= "Hello World!"+i;


                ProducerRecord<String, String> record = new ProducerRecord<>(topic,key, value);
                producer.send(record, (recordMetadata, e) -> {
                    //executes every time a record is sent successfully or an exception is there
                    if (e != null) {
                        LOGGER.error("Error while sending record", e);
                    } else {
                        LOGGER.info("Key :{} \nTopic : {}\nPartition : {}\nOffset : {}\nTimestamp : {}", key, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), recordMetadata.timestamp());
                    }
                });
            }
            Thread.sleep(500);
        }



        producer.flush();
        producer.close();
    }
}
