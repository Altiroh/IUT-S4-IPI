import javax.swing.*;

// Thread g�rant le d�lai de saisie du mdp :
//  - Fait avancer la barre de progression
//  - Est arr�t� ou termine le programme
class Delai extends Thread {
	private JProgressBar barre; // La barre � faire avancer
	private volatile boolean enMarche; // Permet d'arr�ter le thread
	
	public Delai(JProgressBar b) {
		barre = b; // La barre � faire avancer
		enMarche = true; // Le thread est actif
		start(); // Auto d�marrage
	}
	
	public void run() {
		// Tourne tant qu'il n'est pas arr�t� et que la barre n'est pas au max
		while ((barre.getValue() < barre.getMaximum()) && enMarche) {
			try { sleep(100); } // d�lai d'attente = 0,1s
			catch (InterruptedException ie) {
				// Se produit si on appelle arreter() qui fait interrupt()
			}
			if (enMarche) barre.setValue(barre.getValue()+1); // avancer la barre de 1
		}
		if (enMarche) { // La barre est pleine et le thread n'a pas �t� arr�t�
			System.exit(0); // arr�t du programme
		}
	}
	
	public void arreter() { 
		enMarche = false; // sortie de la boucle du run
		interrupt(); // sortie du sleep de la boucle du run
	}
}