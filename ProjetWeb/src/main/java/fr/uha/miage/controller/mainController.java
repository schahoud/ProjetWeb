package fr.uha.miage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.uha.miage.model.Location;
import fr.uha.miage.model.LocationRepository;
import fr.uha.miage.model.LocationRepositoryImpl;
import fr.uha.miage.model.Utilisateur;
import fr.uha.miage.model.UtilisateurRepository;
import fr.uha.miage.model.UtilisateurRepositoryImpl;


@Controller
public class mainController {
	
	@Autowired
	UtilisateurRepository utilisateurRepo ; 
	
	@Autowired
	UtilisateurRepositoryImpl utilisateurRepoImpl ; 
	
	@Autowired
	LocationRepository locationRep ; 
	
	@Autowired
	LocationRepositoryImpl locationRepoImpl ;


	@RequestMapping("/home")
	public String requestCreatePage(Model model){		
		return "home";
	}
	
	
	@RequestMapping("/AjoutUtilisateur")
	public String inscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "ajoutUtilisateur";
    }
	
	
	@RequestMapping(value="/AjoutUtilisateur", method=RequestMethod.POST)
	public String validerInscription(Utilisateur  utilisateur) {
        utilisateurRepo.save(utilisateur);
        
        System.out.println("nbr favoris dans bd : " + utilisateurRepo.count());
		return "redirect:/home";
		
    }
	

	@RequestMapping("/ListeUtilisateurs")
	public String listUtilisateur(Model model) {
		model.addAttribute("liste", utilisateurRepoImpl.printRepClient());
		return "listeUtilisateurs";
    }
	
	@RequestMapping("/ModifUtilisateur")
	public String modifUtilisateur(Model model, Long id) {
		model.addAttribute("utilisateur", utilisateurRepoImpl.rechercheUtilisateurById(id));
		return "modifUtilisateur";
    }
	
	@RequestMapping(value="/ModifUtilisateur", method=RequestMethod.POST)
	public String validerModifUtilisateur(Utilisateur utilisateur) {
		 //utilisateurRepo.delete(utilisateur.getId());  
		 utilisateurRepo.save(utilisateur);
	     System.out.println("nbr favoris dans bd : " + utilisateurRepo.count());
	     return "redirect:/home";
    }
	
	@RequestMapping("/SuppUtilisateur")
	public String suppUtilisateur(Model model, Long id) {
		utilisateurRepoImpl.suppUtilisateurById(id);
		return "home";
    }
	
	@RequestMapping(value="/Login")
	public String loginUser(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "connexion";
    }
	
	@RequestMapping(value="/Login", method=RequestMethod.POST)
	public String verifLoginUser(Model model, Utilisateur utilisateur, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//if email fau	afficher msg
		//if email bon et mp fau  increm 1
		//if email bon et mp bon	initi 0
		String email = utilisateur.getEmail();
		String mot_de_passe = utilisateur.getMot_de_passe();
		
		model.addAttribute("utilisateur", new Utilisateur());
		
		Utilisateur u = null;
		if (utilisateurRepo.findByEmail(email).size() != 0)
		{	u = utilisateurRepo.findByEmail(email).get(0);	}
		
		String message;
		if (u == null)
		{
			message = "Utilisateur non enregistré sur le site web";
			model.addAttribute("message", message);
			return "connexion";
		}
		else if (u.getNb_tentatives() == 5)
		{
			message = "Votre compte est bloqué";
			model.addAttribute("message", message);
			return "connexion";
		}
		else if (!u.getMot_de_passe().equals(mot_de_passe))
		{
			message = "Mot de passe erroné";
			model.addAttribute("message", message);
			
			u.setNb_tentatives(u.getNb_tentatives()+1);
			utilisateurRepo.save(u);
			System.out.println("mp erroné");
			return "connexion";
		}
		else
		{
			System.out.println(mot_de_passe + " " + email);
			System.out.println(u.toString());
			u.setNb_tentatives(0);
			utilisateurRepo.save(u);
			return "succes";
		}
		
    }
	
	
	
	
	
	
	
	
	/* Location */
	
	@RequestMapping("/AjoutLocation")
	public String InsererLocation(Model model) {
		model.addAttribute("location", new Location());
		return "ajoutLocation";
    }

	@RequestMapping(value="/AjoutLocation", method=RequestMethod.POST)
	public String validerlocation(Location  location) {
		System.out.println("ici");
		locationRep.save(location);
        
        System.out.println("nbr favoris dans bd : " + locationRep.count());
		return "redirect:/home";
		
    }

	@RequestMapping("/ListeLocation")
	public String listLocation(Model model) {
		model.addAttribute("listeloc", locationRepoImpl.printRepClient());
		List<Location> lo= new ArrayList<Location>();
		lo=locationRepoImpl.printRepClient();
		return "listeLocations";
    }

	@RequestMapping("/ModifLocation")
	public String modifLocation(Model model, Long id) {
		model.addAttribute("location", locationRepoImpl.rechercheLocationById(id));
		return "modifLocation";
    }

	@RequestMapping(value="/ModifLocation", method=RequestMethod.POST)
	public String validerModifLocation(Location location) {
		locationRep.save(location);
	     System.out.println("nbr favoris dans bd : " + locationRep.count());
	     return "redirect:/home";
    }

	@RequestMapping(value="/suppLocation")
	public String validerSuppLocation(Model model, Long id) {
		locationRepoImpl.suppLocationById(id);  
		
	     System.out.println("nbr favoris dans bd : " + utilisateurRepo.count());
	     return "redirect:/home";
    }

	
	
	
	
	@RequestMapping("/succes")
	public String succes(Model model){		
		return "succes";
	}
	
}
