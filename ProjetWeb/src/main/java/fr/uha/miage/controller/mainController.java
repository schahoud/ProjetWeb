package fr.uha.miage.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.uha.miage.FileUpload;
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
	
	@RequestMapping("/Profil")
	public String pageProfil(Model model, HttpServletRequest request){	
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		System.out.println(" Session : " + ((Utilisateur) session.getAttribute("utilisateur")).toString());
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		model.addAttribute("utilisateur", utilisateurRepoImpl.rechercheUtilisateurById(utilisateur.getId()));
		return "profil";
	}
	
	@RequestMapping("/Accueil")
	public String pageAccueil(Model model){		
		
		return "accueil";
	}
	
	
	
	
	
	/* ----------------- Partie utilisateur ----------------- */
	
	@RequestMapping("/AjoutUtilisateur")
	public String inscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "ajoutUtilisateur";
    }
	
	
	@RequestMapping(value="/AjoutUtilisateur", method=RequestMethod.POST)
	public String validerInscription(Model model, Utilisateur  utilisateur, HttpServletRequest request) throws IOException {
		utilisateur.setDate_inscription(new Date());
        utilisateurRepo.save(utilisateur);
        
        HttpSession session = request.getSession();
        session.setAttribute("utilisateur", utilisateur);
        
        String action;
        String message;
        
        action = "Création de votre compte";
        message = "Votre compte a été bien crée. Vous pouvez désohrmais utiliser toutes les fonctionalités de note site web.";
		
        StringBuilder message1 = new StringBuilder();
		message1.append("<HTML>");
		message1.append("<HEAD>");
		message1.append("<TITLE>");
		message1.append("Confirmation de votre inscription");
		message1.append("</TITLE>");
		message1.append("</HEAD>");
	 
		message1.append("<BODY>");
		message1.append("<H1>Confirmation de votre inscription</H1>" + "");
	 
		message1.append("Bonjour "+ utilisateur.getPrenom() + ",");
		message1.append("<br/><br/>");
		message1.append("Vous venez de créer un compte client sur ProjetWeb.com, Bienvenue !");
		message1.append("<br/><br/>");
		message1.append("Vous pouvez vous connecter à votre espace membre à partir de la page suivante : <br/>http://localhost:8080/Login");
		message1.append("<br/><br/>");
		message1.append("Nous espérons que notre service vous apportera entière satisfaction.");
		message1.append("<br/><br/>");
		message1.append("Cordialement,<br/>L'équipe ProjetWeb.com");
		message1.append("</BODY>");
		message1.append("</HTML>");
		Mail.sendGmail("Confirmation de votre inscription", message1.toString(), utilisateur.getEmail());
		
        
        model.addAttribute("action", action);
        model.addAttribute("message", message);
        return "information";
    }
	

	@RequestMapping("/ListeUtilisateurs")
	public String listUtilisateur(Model model) {
		model.addAttribute("liste", utilisateurRepoImpl.printRepClient());
		return "listeUtilisateurs";
    }
	
	@RequestMapping("/ModifUtilisateur") 
	public String modifUtilisateur(Model model, HttpServletRequest request) {		
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
		Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
		model.addAttribute("utilisateur", utilisateurRepoImpl.rechercheUtilisateurById(u.getId()));
		return "modifUtilisateur";
    }
	
	@RequestMapping(value="/ModifUtilisateur", method=RequestMethod.POST)
	public String validerModifUtilisateur(Model model, Utilisateur utilisateur, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";

		utilisateurRepo.save(utilisateur);
		System.out.println("new user : " + utilisateur.toString());
		session.setAttribute("utilisateur", utilisateur);
		
		String action, message;
		
		action = "Modification des informations";
	    message = "Vos informations personnelles ont été modifiées avec succès.";
			
	    model.addAttribute("action", action);
	    model.addAttribute("message", message);
	    return "information";
    }
	
	
	
	
	@RequestMapping("/DemandeReinit") 
	public String demandeReinit(Model model, HttpServletRequest request) {		
		return "demandeReinit";
    }
	
	
	
	@RequestMapping(value="/DemandeReinit", method=RequestMethod.POST) 
	public String demandeReinit2(Model model, HttpServletRequest request) throws IOException {		
		String email = request.getParameter("email");
		
		
		if (utilisateurRepo.findByEmail(email).size() == 0)
		{
			String message = "Ce compte n'est pas enregistré";
			model.addAttribute("message", message);
			return "demandeReinit";
		}
		
		Utilisateur u = utilisateurRepo.findByEmail(email).get(0);
		
		StringBuilder message = new StringBuilder();
		message.append("<HTML>");
		message.append("<HEAD>");
		message.append("<TITLE>");
		message.append("Demande de location");
		message.append("</TITLE>");
		message.append("</HEAD>");
	 
		message.append("<BODY>");
		message.append("<H3>Demande de réinitialisation du mot de passe</H3>" + "");
	 
		message.append("Bonjour "+ u.getPrenom() + ",");
		message.append("<br/><br/>");
		message.append("Suite à votre demande d'obtention d'un nouveau mot de passe.");
		message.append("<br/><br/>");
		message.append("Veuillez cliquer sur le lien suivant pour accéder au formulaire : ");
		message.append("<br/><br/>");
		message.append("http://localhost:8080/Reinitialisation?id="+ u.getId() + "&go=" + u.getMot_de_passe());
		message.append("<br/><br/>");
		message.append("Pour raison de sécurité, si vous n'accedez pas à ce lien dans un délai de 40 minutes, il expirera.");
		message.append("<br/><br/>");
		message.append("Cordialement,<br/>L'équipe ProjetWeb.com");
		message.append("</BODY>");
		message.append("</HTML>");
		Mail.sendGmail("Reinitialisation du mot de passe", message.toString(), u.getEmail());

		
		model.addAttribute("utilisateur", utilisateurRepoImpl.rechercheUtilisateurById(u.getId()));
		
		String action, message1;
		
		action = "Reinitialisation du mot de passe";
	    message1 = "Un email de reinitialisation du mot de passe a été envoyé.";
			
	    model.addAttribute("action", action);
	    model.addAttribute("message", message1);
	    return "information";		
    }
	
	
	@RequestMapping("/Reinitialisation")
	public String Reinitialisation(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("go");
		System.out.println(id + " " + password);
		model.addAttribute("id", id);
		return "reinitPassword";
    }
	
	@RequestMapping(value="/Reinitialisation", method=RequestMethod.POST)
	public String confirmReinitialisation(Model model, HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		String mp = request.getParameter("nouveauMP");
		Utilisateur u = utilisateurRepoImpl.rechercheUtilisateurById(id);
		u.setMot_de_passe(mp);
		utilisateurRepoImpl.ajoutUtilisateur(u);
		System.out.println("reinit " + utilisateurRepoImpl.countUtilisateurs());
		return "reinitPassword";
    }
	
	@RequestMapping(value="/ModifPassword")
	public String modifPasswordUtilisateur(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		return "modifPassword";
    }
	
	@RequestMapping(value="/ModifPassword", method=RequestMethod.POST)
	public String ConfirmNewPassword(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		String ancienMP = request.getParameter("ancienMP");
		String nouveauMP = request.getParameter("nouveauMP");
		
		System.out.println("ancien mp : " + ancienMP);
		
		Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
		if (u.getMot_de_passe().equals(ancienMP))
		{
			Utilisateur u2 = utilisateurRepo.findByEmail(u.getEmail()).get(0);
			u2.setMot_de_passe(nouveauMP);
			utilisateurRepo.save(u2);
			session.setAttribute("utilisateur", u2);
		}
		else
		{
			String message;
			message = "Mot de passe érroné";
			model.addAttribute("message", message);
			return "modifPassword";
		}

		String action, message;
		
		action = "Modification du mot de passe";
	    message = "Votre mot de passe a été modifié avec succès.";
			
	    model.addAttribute("action", action);
	    model.addAttribute("message", message);
		return "information";
    }
	
	@RequestMapping("/SuppUtilisateur")
	public String suppUtilisateur(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
		Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
		
		System.out.println(u.getId());
		utilisateurRepoImpl.suppUtilisateurById(u.getId());
		
		String action;
        String message;
        
        action = "Suppression du compte";
        message = "Votre compte a été bien supprimé. A bientôt.";
		
        session.removeAttribute("utilisateur");
        
        model.addAttribute("action", action);
        model.addAttribute("message", message);
        return "information";
    }
	
	@RequestMapping(value="/Login")
	public String loginUser(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "connexion";
    }
	
	@RequestMapping(value="/Login", method=RequestMethod.POST)
	public String verifLoginUser(Model model, Utilisateur utilisateur, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
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
			u.setNb_tentatives(0);
			utilisateurRepo.save(u);
			session.setAttribute("utilisateur", u);
			System.out.println(" Session : " + ((Utilisateur) session.getAttribute("utilisateur")).toString());
			return "redirect:/Accueil";
		}
		
    }
	
	
	@RequestMapping(value="/Logout")
	public String logoutUser(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("utilisateur");
		return "/Accueil";
    }
	
	
	
	/* ----------------- Partie location (annonce) ----------------- */
	
	@RequestMapping("/AjoutLocation")
	public String InsererLocation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
		model.addAttribute("location", new Location());
		return "ajoutLocation";
    }
	

	@RequestMapping(value="/AjoutLocation", method=RequestMethod.POST)
	public String validerlocation(Model model, Location  location, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		
		location.setDate_publication(new Date());
		location.setIdutilisateur(utilisateur.getId());
		locationRep.save(location);
        
		String action;
        String message;
        
        action = "Publiez votre annonce";
        message = "Votre annonce a été bien crée.";
		
        System.out.println(location);
        
        String equipement = request.getParameter("equipements");
        System.out.println(equipement);
        
        FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file1", location.getId() + "_file1.jpeg");
		FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file2", location.getId() + "_file2.jpeg");
		FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file3", location.getId() + "_file3.jpeg");
		 
		
		StringBuilder message1 = new StringBuilder();
		message1.append("<HTML>");
		message1.append("<HEAD>");
		message1.append("<TITLE>");
		message1.append("Confirmation de partage d'une annonce");
		message1.append("</TITLE>");
		message1.append("</HEAD>");
	 
		message1.append("<BODY>");
		message1.append("<H1>Confirmation de partage d'une annonce</H1>" + "");
	 
		message1.append("Bonjour "+ utilisateur.getPrenom() + ",");
		message1.append("<br/><br/>");
		message1.append("Vous venez partager une annonce sur ProjetWeb.com.");
		message1.append("<br/><br/>");
		message1.append("Titre de l'annonce : " + location.getTitre());
		message1.append("<br/><br/>");
		message1.append("Vous pouvez visualiser votre annonce à partir de la ppage suivante : <br/>http://localhost:8080/DetailsLocation?id="+location.getId());
		message1.append("<br/><br/>");
		message1.append("Merci pour votre confiance à notre service.");
		message1.append("<br/><br/>");
		message1.append("Cordialement,<br/>L'équipe ProjetWeb.com");
		message1.append("</BODY>");
		message1.append("</HTML>");
		Mail.sendGmail("Confirmation de partage d'une annonce", message1.toString(), utilisateur.getEmail());
		
        
        model.addAttribute("action", action);
        model.addAttribute("message", message);
		return "information";
		
    }

	@RequestMapping("/ListeLocation")
	public String listLocation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		System.out.println(" Session : " + ((Utilisateur) session.getAttribute("utilisateur")).toString());
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		model.addAttribute("listeloc", locationRep.findByIdutilisateur(utilisateur.getId()));
		return "listeLocations";
    }
	
	@RequestMapping("/MesLocations")
	public String mesLocation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		System.out.println(" Session : " + ((Utilisateur) session.getAttribute("utilisateur")).toString());
		
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		System.out.println("Mes loc :" + utilisateur.getId() + " =>" + locationRep.findByIdutilisateur(utilisateur.getId()));
		model.addAttribute("listeloc", locationRep.findByIdutilisateur(utilisateur.getId()));
		return "listeLocations";
    }

	@RequestMapping("/ModifLocation")
	public String modifLocation(Model model, Long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
		id = Long.parseLong(request.getParameter("id"));
		model.addAttribute("location", locationRepoImpl.rechercheLocationById(id));
		return "modifLocation";
    }

	@RequestMapping(value="/ModifLocation", method=RequestMethod.POST)
	public String validerModifLocation(Location location, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
		locationRep.save(location);
	     System.out.println("nbr favoris dans bd : " + locationRep.count());
	     return "redirect:/MesLocations";
    }

	@RequestMapping(value="/SuppLocation")
	public String validerSuppLocation(Model model, Long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
			return "redirect:/Accueil";
		
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
		
	    return "rechercheLocation";
    }
	
	
	
	@RequestMapping("/DetailsLocation")
	public String detailsLocation(Model model, HttpServletRequest request) {
		
		Long id = Long.parseLong(request.getParameter("id"));
		//String ville = request.getParameter("ville");
		//String date_debut = request.getParameter("date_debut");
		//String date_fin = request.getParameter("date_fin");
		//String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		/*model.addAttribute("location", locationRepoImpl.rechercheLocationById(Long.parseLong(id)));
		model.addAttribute("ville", ville);
		model.addAttribute("nb_voyageurs", nb_voyageurs);
		model.addAttribute("date_debut", date_debut);
		model.addAttribute("date_fin", date_fin);*/
		
		/*
		System.out.println("id est : " + id);
		System.out.println("ville est : " + ville);
		System.out.println("date début est : " + date_debut);
		System.out.println("date fin est : " + date_fin);
		System.out.println("nb voyageurs est : " + nb_voyageurs);
		
		Reservation resa = new Reservation(null, Long.parseLong(id), new Date(), new Date(), Integer.parseInt(nb_voyageurs), 0);
		reservationsRepoImpl.ajoutReservation(resa);
		System.out.println(reservationsRepoImpl.countReservation());*/
		
		model.addAttribute("location", locationRepoImpl.rechercheLocationById(id));
		return "detailsLocation";
    }
	
	
	@RequestMapping(value="/Reservation")
	public String reservation(Model model, HttpServletRequest request) {
		
		Long id = Long.parseLong(request.getParameter("id"));
		//String ville = request.getParameter("ville");
		//String date_debut = request.getParameter("date_debut");
		//String date_fin = request.getParameter("date_fin");
		//String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		/*model.addAttribute("location", locationRepoImpl.rechercheLocationById(Long.parseLong(id)));
		model.addAttribute("ville", ville);
		model.addAttribute("nb_voyageurs", nb_voyageurs);
		model.addAttribute("date_debut", date_debut);
		model.addAttribute("date_fin", date_fin);*/
		
		/*
		System.out.println("id est : " + id);
		System.out.println("ville est : " + ville);
		System.out.println("date début est : " + date_debut);
		System.out.println("date fin est : " + date_fin);
		System.out.println("nb voyageurs est : " + nb_voyageurs);
		
		Reservation resa = new Reservation(null, Long.parseLong(id), new Date(), new Date(), Integer.parseInt(nb_voyageurs), 0);
		reservationsRepoImpl.ajoutReservation(resa);
		System.out.println(reservationsRepoImpl.countReservation());*/
		
		model.addAttribute("location", locationRepoImpl.rechercheLocationById(id));
		return "ajoutReservation";
    }
	
	@RequestMapping(value="/confirmReservation")
	public String confirmerReservation(Model model, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("utilisateur") == null)
		{
			String action = "Reservation";
			String message = "Vous devez être connecté pour passer une reservation !";
			model.addAttribute("action", action);
	        model.addAttribute("message", message);
			return "information";
		}
		
		Long idLocation = Long.parseLong(request.getParameter("id"));
		Long idUtilisateur = ((Utilisateur)session.getAttribute("utilisateur")).getId();
		
		
		String date_debut =  request.getParameter("date_debut");
		String date_fin = request.getParameter("date_fin");
		String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		String email = request.getParameter("email");
		String commentaire = request.getParameter("commentaire");
		String telephone = request.getParameter("telephone");

		//System.out.println("conf resa : " + idUtilisateur + " " + idLocation + " " + Integer.parseInt(nb_voyageurs));
		Reservation resa = new Reservation(idUtilisateur, idLocation, new Date(), new Date(), Integer.parseInt(nb_voyageurs), 0);
		reservationsRepoImpl.ajoutReservation(resa);
		
		Location loc = locationRepoImpl.rechercheLocationById(idLocation);
		Utilisateur demandeur = utilisateurRepoImpl.rechercheUtilisateurById(idUtilisateur);	
		Utilisateur recepteur = utilisateurRepoImpl.rechercheUtilisateurById(loc.getIdutilisateur());
		
		
		StringBuilder message1 = new StringBuilder();
		message1.append("<HTML>");
		message1.append("<HEAD>");
		message1.append("<TITLE>");
		message1.append("Confirmation de votre reservation");
		message1.append("</TITLE>");
		message1.append("</HEAD>");
	 
		message1.append("<BODY>");
		message1.append("<H1>Confirmation de votre reservation</H1>" + "");
	 
		message1.append("Bonjour "+ demandeur.getPrenom() + ",");
		message1.append("<br/><br/>");
		message1.append("Vous venez de passer une reservation sur notre plateforme pour la location suivante :");
		message1.append("<br/><br/>");
		message1.append("<b>" + loc.getTitre() + "</b>");
		message1.append("<br/><br/>");
		message1.append("Vous avez joigné ce commentaire à votre demande :");
		message1.append("<br/>");
		message1.append(commentaire);
		message1.append("<br/><br/>");
		message1.append("Cordialement,<br/>L'équipe ProjetWeb.com");
		message1.append("</BODY>");
		message1.append("</HTML>");
		Mail.sendGmail("Confirmation de votre reservation", message1.toString(), email);
		
		/*
		Mail.sendGmail("Confirmation de votre reservation", 
		
		*/
		
		StringBuilder message = new StringBuilder();
		message.append("<HTML>");
		message.append("<HEAD>");
		message.append("<TITLE>");
		message.append("Demande de location");
		message.append("</TITLE>");
		message.append("</HEAD>");
	 
		message.append("<BODY>");
		message.append("<H1>Demande de location</H1>" + "");
	 
		message.append("Bonjour "+ recepteur.getPrenom() + ",");
		message.append("<br/><br/>");
		message.append("Une personne vient de faire une demande de reservation pour votre annonce :");
		message.append("<br/><br/>");
		message.append("<b>" + loc.getTitre() + "</b>");
		message.append("<br/><br/>");
		message.append("Cordonnées du demandeur :");
		message.append("<br/><br/>");
		message.append(demandeur.getPrenom() + " " + demandeur.getNom() + " ("+ email + " / " + telephone +  ")");
		message.append("<br/><br/>");
		message.append("La personne qui a réservée vous a laissée ce commentaire :");
		message.append("<br/>");
		message.append(commentaire);
		message.append("<br/><br/><br/>");
		message.append("Cordialement,<br/>L'équipe ProjetWeb.com");
		message.append("</BODY>");
		message.append("</HTML>");
		Mail.sendGmail("Demande de location", message.toString(), recepteur.getEmail());

		
		String action;
        String notif;
        
        action = "Confirmation de votre réservation";
        notif = "Votre demande de réservation a été bien envoyée. Vous allez recevoir un mail de confirmation sur " + email;
		
        model.addAttribute("action", action);
        model.addAttribute("message", notif);
		return "information";
    }
	
	
	
	@RequestMapping(value="/Reservation", method=RequestMethod.POST)
	public String confirmReservation(Model model, HttpServletRequest request) {
		// à supprimer
		
		
		String email = request.getParameter("email");
		String commentaire = request.getParameter("commentaire");
		//String ville = request.getParameter("ville");
		//String date_debut = request.getParameter("date_debut");
		//String date_fin = request.getParameter("date_fin");
		//String nb_voyageurs = request.getParameter("nb_voyageurs");
		
		/*model.addAttribute("location", locationRepoImpl.rechercheLocationById(Long.parseLong(id)));
		model.addAttribute("ville", ville);
		model.addAttribute("nb_voyageurs", nb_voyageurs);
		model.addAttribute("date_debut", date_debut);
		model.addAttribute("date_fin", date_fin);*/
		
		
		System.out.println("email est : " + email);
		System.out.println("comm est : " + commentaire);/*
		System.out.println("ville est : " + ville);
		System.out.println("date début est : " + date_debut);
		System.out.println("date fin est : " + date_fin);
		System.out.println("nb voyageurs est : " + nb_voyageurs);
		*/
		
		//Reservation resa = new Reservation(null, id, new Date(), new Date(), 1, 0);
		//reservationsRepoImpl.ajoutReservation(resa);
		//System.out.println(reservationsRepoImpl.countReservation());
		
		//model.addAttribute("location", locationRepoImpl.rechercheLocationById(id));
		return "ajoutReservation";
    }
	
	/* ----------------- Global informations ----------------- */
	
	@RequestMapping(value="/EnvoiMail")
	public String envoiMail(Model model) throws IOException {
		
		Mail.sendGmail("Création d'une location", 
				"Bonjour cher client, \n\nVotre location a été bien crée", 
				"s.chahoud.215@gmail.com");
	    return "home";
    }
	
	
	@RequestMapping("/succes")
	public String succes(Model model){		
		return "succes";
	}
	
	
	@RequestMapping(value="/File")
	public String fileUpload(Model model, HttpServletRequest request) {
		return "fileupload";
    }
	
	@RequestMapping(value="/File", method=RequestMethod.POST)
	public String fileUpload1(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("bonjour");
		FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file1", "monFichier1.jpeg");
		FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file2", "monFichier2.jpeg");
		FileUpload.uploadProcess("C:\\Users\\soufi\\uploads", request, response, "file3", "monFichier3.jpeg");
		   
		return "succes";
		}
}
