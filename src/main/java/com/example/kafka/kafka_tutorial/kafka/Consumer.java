package com.example.kafka.kafka_tutorial.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafka.kafka_tutorial.dao.DAO;
import com.example.kafka.kafka_tutorial.pojo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
	
	private final DAO dao;

	@KafkaListener(topics = "tutorial", groupId = "myGroup")
	void consumer(String message) {
		log.info("Message recieved from broker is: {}", message);
	}
	
	@KafkaListener(topics="userJson", groupId="myGroup")
	void userConsumer(User user) {
		log.info("User details recieved: {}", user);
		dao.save(user);
	}
}
