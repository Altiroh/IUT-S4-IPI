import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FenetreIdentification extends JFrame {
	private JTextField saisie; // zone de saisie du mdp
	private JProgressBar attente; // barre de progression
	private final String mdp = "java"; // Code de validation 

	public FenetreIdentification(String nom) {
		// cr�ation de le fen�tre
		super(nom);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// placement des composants d'interface (3 lignes)
		GridLayout placeur=new GridLayout(3,1,1,1);
		getContentPane().setLayout(placeur);

		// Ajout des composants d'interface
		getContentPane().add(new JLabel(" Tapez le code de validation "));
		saisie=new JTextField(); // Saisie du code
		saisie.addActionListener(new MotDePasse()); // Action lors de la saisie
		getContentPane().add(saisie);
		attente=new JProgressBar(JProgressBar.HORIZONTAL ,0, 100); // Barre
		getContentPane().add(attente);

		pack();
		setVisible(true);
	}
	
	private class MotDePasse implements ActionListener { // saisie d'un code
		public synchronized void actionPerformed(ActionEvent e) {
			if (saisie.getText().equals(mdp)) { // Le code est bon
				new InterfaceSauveRecupereMenu("Editeur"); // Lancer l'�diteur
				dispose(); // Fermer cette fen�tre
			}
			else saisie.setText(""); // Autoriser un autre essai
		}
	}

	static public void main(String argv[]) { // Lancement
		new FenetreIdentification("Identification");
	}

}