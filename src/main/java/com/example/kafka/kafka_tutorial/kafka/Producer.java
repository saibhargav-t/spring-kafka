package com.example.kafka.kafka_tutorial.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.kafka.kafka_tutorial.pojo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final KafkaTemplate<String, User> kafkaTemplate2;
	
	public void sendMessage(String message) {
		log.info("Sending message from Producer to Broker");
		kafkaTemplate.send("tutorial", message);
		log.info("Message to Produce: {}", message);
	}
	
	public void sendUser(User user) {
		log.info("Sending User details to Broker...");
		kafkaTemplate2.send("userJson",user);
	}
	
}
