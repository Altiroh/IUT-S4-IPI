import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

public class WebServiceComplet extends JFrame {
	// interface
	private JButton creer, tirer; // Boutons de cr�ation du jeu et tirage d'une carte
	private JLabel carte; // Affichage de l'image de la carte tir�e
	private JTextField erreur; // Zone d'affichage de messages
	private String idJeu; // Identifiant du jeu lorsqu'on en a cr�� un

	// URL du web service
	private final String webService = "https://deckofcardsapi.com/api";
	// Fin de l'URL pour cr�ation d'un jeu
	private final String parametreNouveau = "deck/new/shuffle/?deck_count=1";
	// Caract�re de constitution de l'URL
	private final String separateurParties = "/"; // s�parateur de parties
	// Caract�res sp�ciaux utilis�s dans les r�ponses
	private final String guillemet = "\"";
	private final String valeur = ":";
	// Mots cl�s utilis�s dans les r�ponses
	private final String ident = "deck_id"; // id du jeu
	private final int OK = 200; // R�ponse OK du protocole HTTP
	
	/*****************************************/
	// Fin de l'URL pour tirage d'une carte
	private final String parametreCarte1 = "deck"; // pr�c�de l'id du jeu
	private final String parametreCarte2 = "draw/?count=1"; // suit l'id du jeu
	private final String imageUrl = "image"; // mot cl� pour l'URL de l'image
	/*****************************************/
	
	public WebServiceComplet() {
		super("Acces Web Service");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Mise en place de l'interface
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		creer = new JButton("Creer un jeu");
		creer.addActionListener(new EnvoiCodeCreer());
		panel.add(creer);
		tirer = new JButton("Tirer une carte");
		tirer.setEnabled(false);
		tirer.addActionListener(new EnvoiCodeTirer());
		panel.add(tirer);
		
		// Ajout de la zone d'affichage d'image (initialement vide donc non visible)
		carte = new JLabel();
		getContentPane().add(carte, BorderLayout.CENTER);
		
		// Ajout de la zone de texte
		erreur = new JTextField(20);
		getContentPane().add(erreur, BorderLayout.SOUTH);
		
		idJeu = null; // Pas de jeu cr�� pour le moment
		
		pack();
		setVisible(true);
	}
	
	

	public static void main(String[] args) {
		// Pour pouvoir cr�er une image par URL :
		System.setProperty("http.agent", "custom_agent");
		// En mettant la ligne suivante on peut enlever au HttpsURLConnection la ligne :
		// connexion.setRequestProperty("User-Agent","custom-agent");
		//System.setProperty("https.agent", "custom_agent");
		
		// Si on utilise un proxy de nom "cache" sur le port "3128" :
		/*
		System.setProperty("http.proxyHost", "cache"); // Pour http
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "cache"); // Pour https
		System.setProperty("https.proxyPort", "3128");
		System.setProperty("java.net.useSystemProxies", "true");  // Toujours
		*/
		
		new WebServiceComplet(); // Lancer l'interface
	}

	// Action li�e au bouton de cr�ation d'un jeu
	private class EnvoiCodeCreer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			idJeu = recupereID();
			if (idJeu != null) {
				tirer.setEnabled(true);
				//erreur.setText("ID du jeu = "+idJeu);
			}
			else erreur.setText("ERREUR : pas de jeu cr��");
		}
	}

	// M�thode qui r�cup�re un ID de jeu cr�� et le renvoie
	private String recupereID() {
		String reponse = communique(webService + separateurParties + parametreNouveau);
		return extraitValeur(reponse,ident);
	}

	// M�thode qui �tablit une connexion HTTPs avec le service web par get
	// Le param�tre est l'URL qui d�pend de l'op�ration demand�e
	// Renvoie la r�ponse obteneue ou null si erreur
	private String communique(String chemin) {
        try {
			// Envoi de la requete par get
	        URL urlQuestion = new URL(chemin); // URL et connexion HTTP
            HttpsURLConnection connexion = (HttpsURLConnection) urlQuestion.openConnection();
            // Si on n'a pas mis System.setProperty("https.agent", "custom_agent"); dans main :
			connexion.setRequestProperty("User-Agent","custom-agent"); // car HTTPs
            // test de r�ponse HTTP il faut que ce soit OK = 200
            if (connexion.getResponseCode() != OK) {
	            erreur.setText("ERREUR  de connexion au service : "+connexion.getResponseCode());
				return null; // si erreur terminer
            }
            else {
	            // Lecture du r�sultat en texte
	            BufferedReader lecture = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
	            String ligne; // chaque ligne lue dans la r�ponse
	            String recu = ""; // R�ponse compl�te re�ue
	            while ((ligne = lecture.readLine()) != null) { 
		            recu = recu + ligne+ "\n";
	            }
	            // fermeture de la connexion
	            lecture.close();
	            connexion.disconnect();
	            return recu; // renvoyer la r�ponse du serveur
            }
        }
        catch (IOException ioe) {
        	erreur.setText("ERREUR de connexion au service");
         	return null;
        }
	}

	// Extrait la valeur associee � un mot cl�
	// La forme est :
	// "mot cl�" : "valeur"	
	// Renvoie la valeur ou null si elle n'y est pas
	private String extraitValeur(String ligne, String cle) {
		String cleDebut = guillemet + cle + guillemet; // mot cl� cherch� avec guillemets
		if (!ligne.contains(cleDebut))
			return null; // si le mot cl� n'y est pas on n'a rien
		else { // Sinon on r�cup�re la valeur
			int debut = ligne.indexOf(cleDebut) + cleDebut.length(); // position de la fin du mot cl�
			debut = ligne.indexOf(guillemet,debut)+1; // position du d�but de la valeur
			int fin = ligne.indexOf(guillemet,debut); // position de la fin de la valeur
			return ligne.substring(debut, fin); // texte extrait entre ces 2 positions
		}
	}
	
	
	/****************************************************
	// Ajout� pour tirage de cartes
	/****************************************************/
	
	// Action li�e au bouton de tirage d'une carte
	private class EnvoiCodeTirer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageIcon laCarte = recupereCarte(idJeu);
			if (laCarte != null) {
    			// Afficher l'image obtenue dans le JLabel
				carte.setIcon(laCarte);
				pack(); // Pour que la fen�tre s'adapte � la taille de l'image
			}
			else {
				erreur.setText("ERREUR : pas d'image r�cup�r�e");
			}
		}
	}

	// R�cup�re une image de carte
	// Renvoie l'image ou null si on n'a pas pu la r�cup�rer
	private ImageIcon recupereCarte(String ident) {
		// Connexion pour r�cup�rer une carte
		String reponse = communique(webService + separateurParties + parametreCarte1 + 
							separateurParties + ident + separateurParties + parametreCarte2);
		String chemin = extraitValeur(reponse, imageUrl);
		if (chemin == null) return null;
		else { // On a r�cup�r� un chemin pour l'image
			try {
				// Tentative de connexion � l'URL de l'image
				URL cheminImage = new URL(chemin);
				return new ImageIcon(cheminImage);
			} 
			catch (MalformedURLException mue) { return null; }
		} 
	}

}