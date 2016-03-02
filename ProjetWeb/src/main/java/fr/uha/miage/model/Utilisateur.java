package fr.uha.miage.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Utilisateur implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private Long id;
	private String nom;
	private String prenom;
	private String sexe;
	private String adresse;
	private String ville;
	private int bloque;		//mis à 1 après 5 tentatives
	private Date date_inscription;
	private Date date_naissance;
	
	private String email;
	private String mot_de_passe;
	private int nb_tentatives;	//nombre de mauvaises tentatives de cnx
	
	
	
	
	public Utilisateur() {
		super();
	}


	public Utilisateur(String nom, String prenom, String sexe, String adresse,
			String ville, int bloque, Date date_inscription,
			Date date_naissance, String email, String mot_de_passe, int nb_tentatives) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.adresse = adresse;
		this.ville = ville;
		this.bloque = bloque;
		this.date_inscription = date_inscription;
		this.date_naissance = date_naissance;
		this.email = email;
		this.mot_de_passe = mot_de_passe;
		this.nb_tentatives = nb_tentatives;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public void setBloque(int bloque) {
		this.bloque = bloque;
	}


	public Date getDate_inscription() {
		return date_inscription;
	}


	public void setDate_inscription(Date date_inscription) {
		this.date_inscription = date_inscription;
	}


	public Date getDate_naissance() {
		return date_naissance;
	}


	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMot_de_passe() {
		return mot_de_passe;
	}


	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	
	
	
	public int getNb_tentatives() {
		return nb_tentatives;
	}


	public void setNb_tentatives(int nb_tentatives) {
		this.nb_tentatives = nb_tentatives;
	}


	public int getBloque() {
		return bloque;
	}


	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", sexe=" + sexe + ", adresse=" + adresse + ", ville="
				+ ville + ", bloque=" + bloque + ", date_inscription="
				+ date_inscription + ", date_naissance=" + date_naissance
				+ ", email=" + email + ", mot_de_passe=" + mot_de_passe
				+ ", nb_tentatives=" + nb_tentatives + "]";
	}


	
	
	
	
	
}
