import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class FenetreSaisie extends JFrame {
private JTextArea saisie;
private JTextField titre, balise;
private JComboBox<String> aide;

//***********************
private Vector<String> lesBalises; // balises m�moris�es
//***********************

public FenetreSaisie(String nom) {
	// cr�ation de le fen�tre
	super(nom);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//***********************
	lesBalises=new Vector<String>(); // balises m�moris�es
	//***********************

	// placement des composants
	GridBagLayout placeur=new GridBagLayout();
	getContentPane().setLayout(placeur);
	GridBagConstraints regles=new GridBagConstraints();
	regles.gridx=0; regles.gridy=0;
	regles.gridwidth=2; regles.gridheight=1;
	regles.weightx=100; regles.weighty=100;
	regles.fill=GridBagConstraints.BOTH;
	regles.insets=new Insets(1,1,1,1);
	regles.ipadx=2; regles.ipady=2;

	// Cr�ation et placement des composants
	saisie=new JTextArea();
	JScrollPane zoneSaisie=new JScrollPane(saisie,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	zoneSaisie.setPreferredSize(new Dimension(300,400));
	placeur.setConstraints(zoneSaisie, regles);
	getContentPane().add(zoneSaisie);
	
	titre=new JTextField();
	//***********************
	titre.addActionListener(new ActionTextes()); // ecouteur
	//***********************
	regles.gridx=0; regles.gridy=1;
	regles.gridwidth=1; regles.gridheight=1;
	regles.weightx=50; regles.weighty=0;
	placeur.setConstraints(titre, regles);
	getContentPane().add(titre);
	
	balise=new JTextField();
	//***********************
	balise.addActionListener(new ActionTextes()); // c'est le m�me ecouteur
	//***********************
	regles.gridx=1; regles.gridy=1;
	placeur.setConstraints(balise, regles);
	getContentPane().add(balise);

	aide=new JComboBox<String>();
	//***********************
	aide.addActionListener(new ActionChoix()); // ecouteur
	//***********************
	regles.gridx=0; regles.gridy=2;
	regles.gridwidth=2; regles.gridheight=1;
	placeur.setConstraints(aide, regles);
	getContentPane().add(aide);

	// on peut afficher le tout
	pack();
	setVisible(true);
	}

	public static void main(String arg[]) { 
		new FenetreSaisie("Editeur"); 
	}


// classe associ�e aux �v�nements de saisie de balises ou mots cl�s
//*****************************************************************
private class ActionTextes implements ActionListener {
	public synchronized void actionPerformed(ActionEvent e) {
		String leTitre=titre.getText();
		String laBalise=balise.getText();
		if ((leTitre.length()!=0)&&(laBalise.length()!=0)) { // les 2 champs sont non vides
			lesBalises.add(laBalise); // m�morisation de la balise correspondante
			aide.addItem(leTitre); // ajout � la liste
			titre.setText(""); // on efface
			balise.setText("");
			}
	}
}


// classe associ�e aux �v�nements de la liste d�roulante
//******************************************************
private class ActionChoix implements ActionListener {
	public synchronized void actionPerformed(ActionEvent e) { 
		String balises=lesBalises.elementAt(aide.getSelectedIndex()); // balise correspondante
		saisie.insert(balises,saisie.getCaretPosition()); // insertion dans le texte
	}
}

}