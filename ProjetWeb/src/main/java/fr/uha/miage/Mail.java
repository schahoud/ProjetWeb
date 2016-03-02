package fr.uha.miage;

import java.io.IOException;
import java.util.*;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;




public class Mail {
   
	   public static boolean sendGmail(String objet, String messageCorps, String destinataire) throws IOException {
		   final String username = "fr.uha.miage.projetweb@gmail.com";
			final String password = "projetweb2016.";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("fr.uha.miage.projetweb@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destinataire));
				message.setSubject(objet);
				//message.setText(messageCorps);
				message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageCorps, "text/html")));
				  
				Transport.send(message);

				System.out.println("Email sent !");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

	         return true;
	   }
	   


}
