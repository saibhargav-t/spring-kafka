package com.example.kafka.kafka_tutorial.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
		log.info("Sending message from Producer to Broker");
		kafkaTemplate.send("tutorial", message);
		log.info("Message to Produce: {}", message);
	}
}
