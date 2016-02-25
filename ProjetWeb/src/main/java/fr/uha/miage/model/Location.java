package fr.uha.miage.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Location implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private Long id_utilisateur;	//propriétaire
	private String titre;
	private String resume;
	private String adresse;
	private String ville;
	private String pays;
	private String type_propriete;
	private String type_logement;
	private String capacite_accueil;
	private String nbr_chambres;
	private String nbr_lits;
	private String nbr_sallesBain;
	private String equipements; //télévision, clim, internet ...
	private String photo1;
	private String photo2;
	private String photo3;
	private Double prix_unit;
	private String devise; //EUR, USD ...
	
	
	public Location() {
		super();
	}


	public Location(Long id_utilisateur, String titre, String resume,
			String adresse, String ville, String pays, String type_propriete,
			String type_logement, String capacite_accueil, String nbr_chambres,
			String nbr_lits, String nbr_sallesBain, String equipements,
			String photo1, String photo2, String photo3, Double prix_unit,
			String devise) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.titre = titre;
		this.resume = resume;
		this.adresse = adresse;
		this.ville = ville;
		this.pays = pays;
		this.type_propriete = type_propriete;
		this.type_logement = type_logement;
		this.capacite_accueil = capacite_accueil;
		this.nbr_chambres = nbr_chambres;
		this.nbr_lits = nbr_lits;
		this.nbr_sallesBain = nbr_sallesBain;
		this.equipements = equipements;
		this.photo1 = photo1;
		this.photo2 = photo2;
		this.photo3 = photo3;
		this.prix_unit = prix_unit;
		this.devise = devise;
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


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getResume() {
		return resume;
	}


	public void setResume(String resume) {
		this.resume = resume;
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


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public String getType_propriete() {
		return type_propriete;
	}


	public void setType_propriete(String type_propriete) {
		this.type_propriete = type_propriete;
	}


	public String getType_logement() {
		return type_logement;
	}


	public void setType_logement(String type_logement) {
		this.type_logement = type_logement;
	}


	public String getCapacite_accueil() {
		return capacite_accueil;
	}


	public void setCapacite_accueil(String capacite_accueil) {
		this.capacite_accueil = capacite_accueil;
	}


	public String getNbr_chambres() {
		return nbr_chambres;
	}


	public void setNbr_chambres(String nbr_chambres) {
		this.nbr_chambres = nbr_chambres;
	}


	public String getNbr_lits() {
		return nbr_lits;
	}


	public void setNbr_lits(String nbr_lits) {
		this.nbr_lits = nbr_lits;
	}


	public String getNbr_sallesBain() {
		return nbr_sallesBain;
	}


	public void setNbr_sallesBain(String nbr_sallesBain) {
		this.nbr_sallesBain = nbr_sallesBain;
	}


	public String getEquipements() {
		return equipements;
	}


	public void setEquipements(String equipements) {
		this.equipements = equipements;
	}


	public String getPhoto1() {
		return photo1;
	}


	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}


	public String getPhoto2() {
		return photo2;
	}


	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}


	public String getPhoto3() {
		return photo3;
	}


	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}


	public Double getPrix_unit() {
		return prix_unit;
	}


	public void setPrix_unit(Double prix_unit) {
		this.prix_unit = prix_unit;
	}


	public String getDevise() {
		return devise;
	}


	public void setDevise(String devise) {
		this.devise = devise;
	}


	@Override
	public String toString() {
		return "Location [id=" + id + ", id_utilisateur=" + id_utilisateur
				+ ", titre=" + titre + ", resume=" + resume + ", adresse="
				+ adresse + ", ville=" + ville + ", pays=" + pays
				+ ", type_propriete=" + type_propriete + ", type_logement="
				+ type_logement + ", capacite_accueil=" + capacite_accueil
				+ ", nbr_chambres=" + nbr_chambres + ", nbr_lits=" + nbr_lits
				+ ", nbr_sallesBain=" + nbr_sallesBain + ", equipements="
				+ equipements + ", photo1=" + photo1 + ", photo2=" + photo2
				+ ", photo3=" + photo3 + ", prix_unit=" + prix_unit
				+ ", devise=" + devise + "]";
	}


	
	
}
