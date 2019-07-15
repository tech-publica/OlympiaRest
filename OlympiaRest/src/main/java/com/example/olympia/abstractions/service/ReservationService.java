package com.example.olympia.abstractions.service;

import com.example.olympia.entity.Reservation;

public interface ReservationService {
	Iterable<Reservation> list();

	void save(Reservation reservation);

	void update(Reservation reservation);
	
	Reservation byId(int id);

	void delete(int id);
}
