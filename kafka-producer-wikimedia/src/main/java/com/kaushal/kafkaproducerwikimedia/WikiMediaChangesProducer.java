package com.kaushal.kafkaproducerwikimedia;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WikiMediaChangesProducer {

	public static void main(String[] args) throws InterruptedException {
	String bootstrapServers="127.0.0.1:9092";

	//create producer
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", bootstrapServers);
		props.setProperty("key.serializer", StringSerializer.class.getName());
		props.setProperty("value.serializer", StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
	String topic="wikimedia.recentchange";
		EventHandler eventhandler=new WikiMediaChangeHandler(producer,topic);
		String url="https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource.Builder builder= new EventSource.Builder(eventhandler, URI.create(url));
		EventSource eventSource=builder.build();
		//start producer in another thread
		eventSource.start();

		//we produce for 10 min and block program until then
		TimeUnit.MINUTES.sleep(10);

	}

}
