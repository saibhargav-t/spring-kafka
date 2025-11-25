package com.example.kafka.kafka_tutorial.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.kafka.kafka_tutorial.dao.DAO;
import com.example.kafka.kafka_tutorial.pojo.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DamoImpl implements DAO {
	
	private final EntityManager em;

	@Override
	@Transactional
	public void save(User user) {
		em.persist(user);
	}

}
