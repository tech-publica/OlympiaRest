package com.example.olympia.implementations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.olympia.abstractions.dao.ClientDAO;
import com.example.olympia.abstractions.service.ClientService;
import com.example.olympia.entity.Client;

@Service
public class ClientServiceStandard implements ClientService{

	@Autowired
	private ClientDAO clientDao;
	
	@Override
	@Transactional
	public Client findById(int id) {
		return clientDao.findById(id);
	}

}
