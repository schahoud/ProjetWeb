package fr.uha.miage.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationRepositoryImpl {

	@Autowired
	public ReservationRepository reservationRep ; 
	
	
	public ReservationRepositoryImpl() {
		super();
	} 
	
	public void ajoutReservation(Reservation resa){
		reservationRep.save(resa);
	}
	
	public Reservation rechercheReservationById(Long id) {
		return reservationRep.findOne(id); 
	}

	public void suppReservation() {
		reservationRep.deleteAll();
	}
	
	public void suppReservationById(Long id) {
		reservationRep.delete(id);
	}
	
	public long countReservation(){
		return reservationRep.count(); 
	}
	
	public List<Reservation> printRepReservation() 
	{
		return (List<Reservation>)reservationRep.findAll() ; 
	}
}
