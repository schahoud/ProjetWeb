package fr.uha.miage.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Reservation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	private Long id_utilisateur;
	private Long id_location;
	private Date date_arrivee;
	private Date date_depart;
	private int nb_voyageurs;
	private int resa_instantanee;	// 1: avec confirmation		0: sans confirmation
	
	
	
	public Reservation() {
		super();
	}



	public Reservation(Long id_utilisateur, Long id_location,
			Date date_arrivee, Date date_depart, int nb_voyageurs,
			int resa_instantanee) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_location = id_location;
		this.date_arrivee = date_arrivee;
		this.date_depart = date_depart;
		this.nb_voyageurs = nb_voyageurs;
		this.resa_instantanee = resa_instantanee;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getId_utilisateur() {
		return id_utilisateur;
	}



	public void setId_utilisateur(Long id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}



	public Long getId_location() {
		return id_location;
	}



	public void setId_location(Long id_location) {
		this.id_location = id_location;
	}



	public Date getDate_arrivee() {
		return date_arrivee;
	}



	public void setDate_arrivee(Date date_arrivee) {
		this.date_arrivee = date_arrivee;
	}



	public Date getDate_depart() {
		return date_depart;
	}



	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}



	public int getNb_voyageurs() {
		return nb_voyageurs;
	}



	public void setNb_voyageurs(int nb_voyageurs) {
		this.nb_voyageurs = nb_voyageurs;
	}



	public int getResa_instantanee() {
		return resa_instantanee;
	}



	public void setResa_instantanee(int resa_instantanee) {
		this.resa_instantanee = resa_instantanee;
	}



	@Override
	public String toString() {
		return "Reservation [id=" + id + ", id_utilisateur=" + id_utilisateur
				+ ", id_location=" + id_location + ", date_arrivee="
				+ date_arrivee + ", date_depart=" + date_depart
				+ ", nb_voyageurs=" + nb_voyageurs + ", resa_instantanee="
				+ resa_instantanee + "]";
	}
	
	
	
	
	
	
	
}
