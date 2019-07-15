package com.example.olympia.implementations.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.olympia.abstractions.dao.ReservationDAO;
import com.example.olympia.entity.Reservation;

@Repository
public class HibernateReservationDAO implements ReservationDAO {

	public static final String HQL_SELECT_ALL = "from Reservation";

	@Autowired
	private EntityManager manager;

	@Override
	public Iterable<Reservation> allReservations() {
		//@SuppressWarnings("unchecked")
		List<Reservation> res = manager.createQuery(HQL_SELECT_ALL, Reservation.class)
				                                  .getResultList();
		return res;
	}

	@Override
	public Reservation add(Reservation newReservation) {
		manager.persist(newReservation);
		return newReservation;
	}

	@Override
	public Reservation update(Reservation newReservation) {
		manager.merge(newReservation);
		return newReservation;
	}

	@Override
	public Reservation delete(int idReservation) {
		Reservation found = manager.getReference(Reservation.class, idReservation);
		manager.remove(found);
		return found;
	}

	@Override
	public Reservation findById(int id) {
		Reservation found = manager.find(Reservation.class, id);
		return found;
	}

}
