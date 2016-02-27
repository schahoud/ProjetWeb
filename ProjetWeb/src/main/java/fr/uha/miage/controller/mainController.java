package fr.uha.miage.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.uha.miage.Mail;
import fr.uha.miage.model.Location;
import fr.uha.miage.model.LocationRepository;
import fr.uha.miage.model.LocationRepositoryImpl;
import fr.uha.miage.model.Reservation;
import fr.uha.miage.model.ReservationRepository;
import fr.uha.miage.model.ReservationRepositoryImpl;
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
	
	@Autowired
	ReservationRepository reservationRepo ; 
	
	@Autowired
	ReservationRepositoryImpl reservationsRepoImpl ; 


	@RequestMapping("/home")
	public String requestCreatePage(Model model){		
		return "home";
	}
	
	
	/* ----------------- Partie utilisateur ----------------- */
	
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
	
	
	
	
	
	
	/* ----------------- Partie location (annonce) ----------------- */
	
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
		model.addAttribute("listeloc", locationRepoImpl.printRepLocation());
		//List<Location> lo= new ArrayList<Location>();
		//lo=locationRepoImpl.printRepLocation();
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

	
	
	
	
	
	
	
	/* ----------------- Partie reservation ----------------- */
	
	
	
	/* 1. Recherche d'une location
	 * 2. Affichage des résultats 
	 * 3. Affichage des details d'une location
	 * 4. reserver
	 */
	
	@RequestMapping(value="/RechLocation")
	public String rechLocation(Model model) {
	    return "rechercheLocation";
    }
	
	@RequestMapping(value="/resultRechLocation", method=RequestMethod.POST)
	public String resultRechLocation(Model model, HttpServletRequest request) {
		String ville = request.getParameter("ville");
		String date_debut = request.getParameter("date_debut");
		String date_fin = request.getParameter("date_fin");
		String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		System.out.println("ville est : " + ville);
		System.out.println("date début est : " + date_debut);
		System.out.println("date fin est : " + date_fin);
		System.out.println("nb voyageurs est : " + nb_voyageurs);
		
		model.addAttribute("liste", locationRep.findByVille(ville));
		model.addAttribute("ville", ville);
		model.addAttribute("nb_voyageurs", nb_voyageurs);
		model.addAttribute("date_debut", date_debut);
		model.addAttribute("date_fin", date_fin);
		
	    return "resultRechLocation";
    }
	
	
	
	@RequestMapping("/Reservation")
	public String detailsLocation(Model model, HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String ville = request.getParameter("ville");
		String date_debut = request.getParameter("date_debut");
		String date_fin = request.getParameter("date_fin");
		String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		/*model.addAttribute("location", locationRepoImpl.rechercheLocationById(Long.parseLong(id)));
		model.addAttribute("ville", ville);
		model.addAttribute("nb_voyageurs", nb_voyageurs);
		model.addAttribute("date_debut", date_debut);
		model.addAttribute("date_fin", date_fin);*/
		
		System.out.println("id est : " + id);
		System.out.println("ville est : " + ville);
		System.out.println("date début est : " + date_debut);
		System.out.println("date fin est : " + date_fin);
		System.out.println("nb voyageurs est : " + nb_voyageurs);
		
		Reservation resa = new Reservation(null, Long.parseLong(id), new Date(), new Date(), Integer.parseInt(nb_voyageurs), 0);
		reservationsRepoImpl.ajoutReservation(resa);
		System.out.println(reservationsRepoImpl.countReservation());
		return "detailsLocation";
    }
	
	
	
	
	
	
	/* ----------------- Global informations ----------------- */
	
	@RequestMapping(value="/EnvoiMail")
	public String envoiMail(Model model) {
		
		Mail.sendGmail("Création d'une location", 
				"Bonjour cher client, \n\nVotre location a été bien crée", 
				"s.chahoud.215@gmail.com");
	    return "home";
    }
	
	
	@RequestMapping("/succes")
	public String succes(Model model){		
		return "succes";
	}
	
}
