package com.example.olympia.implementations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.olympia.abstractions.dao.CourtDAO;
import com.example.olympia.abstractions.service.CourtService;
import com.example.olympia.entity.Court;

@Service
public class CourtServiceStandard implements CourtService{
  
	@Autowired
	private CourtDAO courtDao;

	@Override
	@Transactional
	public Court findById(int id) {
		return courtDao.findById(id);
		
	}
	
	
	
	
}
