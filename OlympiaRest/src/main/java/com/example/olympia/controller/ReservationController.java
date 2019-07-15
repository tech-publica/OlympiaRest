package com.example.olympia.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.olympia.abstractions.service.ClientService;
import com.example.olympia.abstractions.service.CourtService;
import com.example.olympia.abstractions.service.ReservationService;
import com.example.olympia.dto.reservation.ReservationDTO;
import com.example.olympia.dto.reservation.ReservationInputDTO;
import com.example.olympia.entity.Client;
import com.example.olympia.entity.Court;
import com.example.olympia.entity.Reservation;

@RestController
@RequestMapping("/api")
public class ReservationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private CourtService courtService;
	
	
	@GetMapping("/reservations")
	public Iterable<ReservationDTO> all() {
		logger.info("calling all reservation method");
		Iterable<Reservation> all = reservationService.list();
		Stream<Reservation> streamRes = StreamSupport.stream(all.spliterator(), false);
		return streamRes.map(r -> ReservationDTO.reservationToDTO(r)).collect(Collectors.toList());
	}
	
	@GetMapping("/reservations/{id}")
	public ReservationDTO find(@PathVariable int id) {
		logger.info("calling find reservation method");
		Reservation found = reservationService.byId(id);
		if (found == null) {
			throw new RuntimeException("reservation not found");
		}
		ReservationDTO dto = ReservationDTO.reservationToDTO(found);
		return dto;
	}
	
	@PostMapping("/reservations")
	public ReservationInputDTO add(@RequestBody ReservationInputDTO dto) {
		logger.info("calling add reservation method");
		Reservation res = dto.toReservation();
		Client client = clientService.findById(dto.getClientId());
		if(client == null) {
			throw new RuntimeException("client not found");
		}
		Court court = courtService.findById(dto.getCourtId());
		if(court == null) {
			throw new RuntimeException("court not found");
		}
		res.setClient(client);
		res.setCourt(court);
		reservationService.save(res);
		ReservationInputDTO result = ReservationInputDTO.reservationToDTO(res);
		return result;
	}
	
	
	@PutMapping("/reservations/{id}")
	public ReservationInputDTO update(@PathVariable int id, @RequestBody ReservationInputDTO dto) {
		logger.info("calling update reservation method");
		if(dto.getId() !=  id) {
			throw new RuntimeException("bad input data");
		}
		Reservation res = dto.toReservation();
		Reservation found = reservationService.byId(id);
		if(found== null) {
			throw new RuntimeException("reservation not found");
		}
		Client client = clientService.findById(dto.getClientId());
		if(client == null) {
			throw new RuntimeException("client not found");
		}
		Court court = courtService.findById(dto.getCourtId());
		if(court == null) {
			throw new RuntimeException("court not found");
		}
		res.setClient(client);
		res.setCourt(court);
		reservationService.update(res);
		ReservationInputDTO result = ReservationInputDTO.reservationToDTO(res);
		return result;
	}
	
	
	@DeleteMapping("/reservations/{id}")
	public String delete(@PathVariable int id) {
		logger.info("calling delete reservation method");
		Reservation found = reservationService.byId(id);
		if(found== null) {
			throw new RuntimeException("reservation not found");
		}
		reservationService.delete(id);
		return String.format("customer with id %d deleted", id);
	}
}








