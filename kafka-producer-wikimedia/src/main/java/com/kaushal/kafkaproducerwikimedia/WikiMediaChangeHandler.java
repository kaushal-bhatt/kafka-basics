package com.kaushal.kafkaproducerwikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiMediaChangeHandler implements EventHandler {

    KafkaProducer<String, String> producer;
    String topic;

    private final Logger LOGGER = LoggerFactory.getLogger(WikiMediaChangeHandler.class.getSimpleName());
    public WikiMediaChangeHandler(KafkaProducer<String, String> producer,String topic) {
        this.producer=producer;
        this.topic=topic;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClosed() {
    producer.close();
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent){
        LOGGER.info("Received message : {}",messageEvent.getData());
    //asynchronus
        producer.send(new ProducerRecord<>(topic,messageEvent.getData()));
    }

    @Override
    public void onComment(String s) {
        //nothing here
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error in the consumer",throwable);
    }
}
