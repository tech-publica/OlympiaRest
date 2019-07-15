package com.example.olympia.implementations.dao.hibernate;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.olympia.abstractions.dao.CourtDAO;
import com.example.olympia.entity.Court;

@Repository
public class HibernateCourtDAO implements CourtDAO {

	@Autowired
	private EntityManager manager;
	
	@Override
	public Court findById(int id) {
		Court found = manager.find(Court.class, id);
		return found;

	}

}
