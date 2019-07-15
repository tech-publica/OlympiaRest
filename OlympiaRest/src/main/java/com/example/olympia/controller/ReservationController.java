package com.example.olympia.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.olympia.abstractions.service.ClientService;
import com.example.olympia.abstractions.service.CourtService;
import com.example.olympia.abstractions.service.ReservationService;
import com.example.olympia.dto.reservation.ReservationDTO;
import com.example.olympia.dto.reservation.ReservationInputDTO;
import com.example.olympia.entity.Client;
import com.example.olympia.entity.Court;
import com.example.olympia.entity.Reservation;
import com.example.olympia.exception.BadRequestException;
import com.example.olympia.exception.ResourceNotFoundException;

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
		if(2 == 1+ 1)
		  throw new NullPointerException();
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
			throw new ResourceNotFoundException("reservation not found");
		}
		ReservationDTO dto = ReservationDTO.reservationToDTO(found);
		return dto;
	}
	
	@PostMapping("/reservations")
	public  ResponseEntity<ReservationInputDTO> add(@RequestBody ReservationInputDTO dto, UriComponentsBuilder uriComponentsBuilder) {
		logger.info("calling add reservation method");
		Reservation res = dto.toReservation();
		Client client = clientService.findById(dto.getClientId());
		if(client == null) {
			return new ResponseEntity<ReservationInputDTO>(HttpStatus.NOT_FOUND);
		}
		Court court = courtService.findById(dto.getCourtId());
		if(court == null) {
			return new ResponseEntity<ReservationInputDTO>(HttpStatus.NOT_FOUND);
		}
		res.setClient(client);
		res.setCourt(court);
		reservationService.save(res);
		ReservationInputDTO result = ReservationInputDTO.reservationToDTO(res);
		UriComponents uriComponents = 
		        uriComponentsBuilder.path("/reservations/{id}").buildAndExpand(result.getId());
		return ResponseEntity.created(uriComponents.toUri()).body(result);
		//return new ResponseEntity<ReservationInputDTO>(result, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/reservations/{id}")
	public ResponseEntity<ReservationInputDTO> update(@PathVariable int id, @RequestBody ReservationInputDTO dto) {
		logger.info("calling update reservation method");
		if(dto.getId() !=  id) {
			throw new BadRequestException("bad input data: id parameter must be equal to reservation id");
		}
		Reservation res = dto.toReservation();
		Reservation found = reservationService.byId(id);
		if(found== null) {
			throw new ResourceNotFoundException("reservation not found");
		}
		Client client = clientService.findById(dto.getClientId());
		if(client == null) {
			throw new ResourceNotFoundException("client not found");
		}
		Court court = courtService.findById(dto.getCourtId());
		if(court == null) {
			throw new ResourceNotFoundException("court not found");
		}
		res.setClient(client);
		res.setCourt(court);
		reservationService.update(res);
		ReservationInputDTO result = ReservationInputDTO.reservationToDTO(res);
		return new ResponseEntity<ReservationInputDTO>(result, HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		logger.info("calling delete reservation method");
		Reservation found = reservationService.byId(id);
		if(found== null) {
			throw new ResourceNotFoundException("reservation not found");
		}
		reservationService.delete(id);
		return ResponseEntity.noContent().build();
	}
}








