package com.example.kafka.kafka_tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.kafka_tutorial.kafka.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Slf4j
public class RestControllers {

	private final Producer kafkaProducer;

	// http://localhost:2619/api/v1/kafka/publish?message=Jai Sri Ram
	@GetMapping("/publish")
	ResponseEntity<String> publish(@RequestParam String message) {
		log.info("User entered message is: {}", message);
		log.info("Sending to Kafka Producer");
		kafkaProducer.sendMessage(message);
		return ResponseEntity.ok("Message sent to the topic successfully!!");
	}

}
