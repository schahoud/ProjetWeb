package fr.uha.miage.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long>{


	List<Utilisateur> findByNom(String nom, String prenom); 
	
	List<Utilisateur> findByEmail(String email); 
}
