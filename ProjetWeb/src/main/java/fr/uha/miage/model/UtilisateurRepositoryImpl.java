package fr.uha.miage.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
@Transactional
public class UtilisateurRepositoryImpl {
	
	@Autowired
	public UtilisateurRepository utilisateurRep ; 
	
	
	public UtilisateurRepositoryImpl() {
		super();
	} 
	
	public void ajoutUtilisateur(Utilisateur c){
		utilisateurRep.save(c);
	}
	
	public Utilisateur rechercheUtilisateurById(Long id) {
		return utilisateurRep.findOne(id); 
	}

	public void suppUtilisateurs() {
		utilisateurRep.deleteAll();
	}
	
	public void suppUtilisateurById(Long id) {
		utilisateurRep.delete(id);
	}
	
	public long countUtilisateurs(){
		return utilisateurRep.count(); 
	}
	
	public List<Utilisateur> printRepClient() 
	{
		return (List<Utilisateur>)utilisateurRep.findAll() ; 
	}

}
