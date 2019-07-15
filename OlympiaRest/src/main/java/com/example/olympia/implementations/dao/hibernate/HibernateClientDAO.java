package com.example.olympia.implementations.dao.hibernate;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.olympia.abstractions.dao.ClientDAO;
import com.example.olympia.entity.Client;

@Repository
public class HibernateClientDAO implements ClientDAO {

	@Autowired
	private EntityManager manager;

	@Override 
	public Client findById(int id) {
		Client found = manager.find(Client.class, id);
		return found;

	}

}
