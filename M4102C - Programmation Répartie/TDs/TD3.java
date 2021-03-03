import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
//************************************
// Ajouté pour FileChooser
//************************************
import javax.swing.filechooser.*;


class InterfaceSauveRecupereMenuChoix extends JFrame {
private JTextArea saisie;
private JTextField titre, balise;
private JComboBox<String> aide;
private Vector<String> lesBalises;
private volatile boolean ajout;  // pour éviter l'appel de l'écouteur lors de l'ajout
private final String fichierSauvegarde = "configuration.ser";

public InterfaceSauveRecupereMenuChoix(String nom) {
	// création de le fenêtre
	super(nom);
	addWindowListener(new GestionFenetre()); 

	JMenuBar barreDeMenu = new JMenuBar();
	JMenu menuFichier = new JMenu("Fichier");
	barreDeMenu.add(menuFichier);
	JMenuItem ouvrir = new JMenuItem("Ouvrir");
	menuFichier.add(ouvrir);
	//***********************************************
	ouvrir.addActionListener(new Ouvrir());
	//***********************************************
	JMenuItem enregistrer = new JMenuItem("Enregistrer");
	menuFichier.add(enregistrer);
	//***********************************************
	enregistrer.addActionListener(new Enregistrer());
	//***********************************************
	setJMenuBar(barreDeMenu);

	if (!lireConfiguration(fichierSauvegarde)) { // si on n'a pas pu récupérer les objets on les crée
		lesBalises=new Vector<String>();
		aide=new JComboBox<String>();
	}

	ajout=false; // init = pas d'ajout en cours

	// placement des composants
	BorderLayout placeur=new BorderLayout();
	getContentPane().setLayout(placeur);

	// Création et placement des composants
	saisie=new JTextArea();
	JScrollPane zoneSaisie=new JScrollPane(saisie,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	zoneSaisie.setPreferredSize(new Dimension(300,400));
	getContentPane().add(zoneSaisie, BorderLayout.CENTER);

	JPanel bas = new JPanel();
	bas.setLayout(new GridLayout(2,1,2,2));
	getContentPane().add(bas, BorderLayout.SOUTH);
		
	 JPanel saisies = new JPanel();
	 saisies.setLayout(new GridLayout(1,2,2,2));
	 bas.add(saisies);
	  
	titre=new JTextField();
	titre.addActionListener(new ActionTextes()); // ecouteur
	saisies.add(titre);
	
	balise=new JTextField();
	balise.addActionListener(new ActionTextes()); // c'est le même ecouteur
	saisies.add(balise);

	aide.addActionListener(new ActionChoix()); // ecouteur
	bas.add(aide);

	// on peut afficher le tout
	pack();
	setVisible(true);
	}

	// classe associée aux événements de saisie de balises ou mots clés
	//*****************************************************************
	private class ActionTextes implements ActionListener {
		public synchronized void actionPerformed(ActionEvent e) {
			String leTitre=titre.getText();
			String laBalise=balise.getText();
			if ((leTitre.length()!=0)&&(laBalise.length()!=0)) { // les 2 champs sont non vides
				lesBalises.add(laBalise); // mémorisation de la balise correspondante
				ajout=true; // ajout en cours
				aide.addItem(leTitre); // ajout à la liste
				ajout=false; // ajout terminé
				titre.setText(""); // on efface
				balise.setText("");
				}
		}
	}

	// classe associée aux événements de la liste déroulante
	//******************************************************
	private class ActionChoix implements ActionListener {
		public synchronized void actionPerformed(ActionEvent e) { 
			if(!ajout) { // si c'est un ajout on ne fait rien
				String balises=lesBalises.elementAt(aide.getSelectedIndex()); // balise correspondante
				saisie.insert(balises,saisie.getCaretPosition()); // insertion dans le texte
			}
		}
	}

	// Recupération de la sauvegarde clés/balises	
	private boolean lireConfiguration(String fichier) {
		boolean ok=false;
		File lit=new File(fichier); // fichier de sauvegarde
		ObjectInputStream entree=null;
		try { 
			entree= new ObjectInputStream(new FileInputStream(lit)); 
			aide=(JComboBox<String>) entree.readObject(); // affichera un warning :
			lesBalises=(Vector<String>) entree.readObject(); // unchecked cast
			entree.close();
			ok=true;
			}
		catch (FileNotFoundException fnf) { 
			System.out.println("Fichier de sauvegarde introuvable");
			}
		catch (ClassNotFoundException cnf){ 
			System.out.println("Lecture des objets impossible : erreur de classe");
			}
		catch (OptionalDataException ode){ 
			System.out.println("Lecture des objets impossible : erreur de données");
			}
		catch (IOException io1) { 
			System.out.println("Création/lecture ou fermeture de flux impossible");
			}
		return ok;
	}
	
	// Classe associée aux événements de la fenêtre 
	// Ajoutée pour sauvegarde par sérialisation
	private class GestionFenetre extends WindowAdapter {
		public synchronized void windowClosing(WindowEvent e) { // fermeture de la fenêtre
			sauverConfiguration(fichierSauvegarde);
			System.exit(0); // arrêt du programme
			}
	}

	// Sauvegarde de configuration (clés/balises)
	private void sauverConfiguration(String fichier) {
		boolean reussi = true;
		File ecrit=new File(fichier); // fichier de sauvegarde
		FileOutputStream flot=null; // flot lié au fichier
		ObjectOutputStream sortie=null; // flot lié au fichier pour écrire des objets
		
		if (ecrit.exists()) ecrit.delete(); // si le fichier existe on le détruit
		try { ecrit.createNewFile(); } // on crée un ficheir vide
		catch (IOException io1) { 
			System.out.println("création impossible du fichier d'enregistrement");
			reussi = false;
		}
		if (reussi) { // Fichier créé => créer le flot d'écriture sur ce fichier
			try { flot=new FileOutputStream(ecrit); }
			catch (FileNotFoundException fnf) {
				System.out.println("Fichier d'enregistrement introuvable");
				reussi = false;
			}
		}
		if (reussi) { // flot d'écriture créé => créer le flot pour objets
			try { sortie=new ObjectOutputStream(flot); }
			catch (IOException io2) { 
				System.out.println("Création du flot objet impossible");
				reussi = false;
			}
		}
		if (reussi) { // flot d'écriture d'objets créé => écrire les objets
			try { 
				sortie.writeObject(aide); // écriture de la JComboBox
				sortie.writeObject(lesBalises); // écriture du Vector
			}
			catch (IOException io3) { 
				System.out.println("Ecriture des objets impossible");
				reussi = false;
			}
		}
		if (sortie != null) { // le flot existe => on le vide et le ferme
			try {sortie.flush(); sortie.close(); } 
			catch (IOException io4) { 
				System.out.println("Fermeture du flot objet impossible");
				reussi = false;
			}
		}
	}

	//******************************************
	// Ajouté pour action menu Ouvrir
	//******************************************
	private class Ouvrir implements ActionListener {
		public synchronized void actionPerformed(ActionEvent e) {
			File fich = choisirFichier();
			if (fich != null) {
				try { 
					BufferedReader entree= new BufferedReader(
							new InputStreamReader(new FileInputStream(fich))); // Flux pour lire
					String ligne;
					saisie.setText("");
					while((ligne=entree.readLine()) != null) { // Lecture
						saisie.append(ligne+"\n");
					}
					entree.close(); // Fermeture du flux
				}
				catch(IOException ioe) {
					System.out.println("Lecture du texte impossible");
				}
			}
			else System.out.println("Pas de fichier sélectionné"); 
		}
	}

	//*********************************************
	// Ajouté pour action menu Enregistrer
	//*********************************************
	private class Enregistrer implements ActionListener {
		public synchronized void actionPerformed(ActionEvent e) {
			File fich = choisirFichier();
			if (fich != null) {
				String nom = fich.getAbsolutePath(); // Nom du fichier choisi
				if (!nom.contains(".")) fich = new File(nom + ".txt"); // Si on n'a pas mis le suffixe .txt on l'ajoute
				try { 
					PrintWriter sortie= new PrintWriter(new FileOutputStream(fich)); // Flux pour écrire
					sortie.println(saisie.getText()); // écriture
					sortie.flush(); // Vidage du flux (au cas où)
					sortie.close(); // fermeture du flux
				}
				catch(IOException ioe) {
					System.out.println("Ecriture du texte impossible");
				}
			}
			else System.out.println("Pas de fichier sélectionné"); 
		}
	}
	
	//****************************************************
	// Ajouté pour choix du fichier texte par FileChooser
	//****************************************************
	private File choisirFichier() {
		JFileChooser choixFichier = new JFileChooser("."); // Répertoire de départ
    	FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichiers éditeur", "txt");
    	choixFichier.setFileFilter(filtre); // Filtre d'extensions
    	int codeRetour = choixFichier.showOpenDialog(this); // ouverture
    	if(codeRetour == JFileChooser.APPROVE_OPTION) { // Fichier sélectionné
            return choixFichier.getSelectedFile(); // récupérer le fichier sélectionné (File)
    	}
    	else return null; // Pas de fichier sélectionné	
    }

	public static void main(String arg[]) { 
		new InterfaceSauveRecupereMenuChoix("Editeur"); 
	}



}