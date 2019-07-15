package com.example.olympia.abstractions.dao;

import com.example.olympia.entity.Reservation;

public interface ReservationDAO {
    Iterable<Reservation> allReservations();
    Reservation add(Reservation newReservation);
    Reservation update(Reservation newReservation);
    Reservation delete (int idReservation);
    Reservation findById(int id);
}
