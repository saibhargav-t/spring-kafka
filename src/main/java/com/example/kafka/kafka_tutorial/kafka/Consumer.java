package com.example.kafka.kafka_tutorial.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Consumer {

	@KafkaListener(topics = "tutorial", groupId = "myGroup")
	void consumer(String message) {
		log.info("Message recieved from broker is: {}", message);
	}
}
