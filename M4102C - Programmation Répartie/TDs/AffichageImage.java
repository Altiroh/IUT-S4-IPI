import javax.swing.JFrame; //Fenêtre

import javax.swing.JLabel; //Affichage de l'image
import javax.swing;ImageIcon; //Image
import java.awt.GridLayout; //Placement en tableau 1x1
import java.awt.FlowLayout; // Ou placement en flow 1 élément

public class AfficheImmage{ // Interface => hérite de JFRAME

private JLabel ecran; //Cette Interface ne contient qu'un JLabel pour une image
 
public AffichageImage (String monImage){ //interface d'affichage recoit le nom du fichier image
    super(monImage); //Nom du fichier image en titre
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//arrêt du programme si fermeture
//GridLayout placeur = new GridLayout(1,1,2,2); //Placement en tableau(GridLayout)
    FlowLayout placeur = new FlowLayout(FlowLayout.CENTER, 20, 40); //Placement FlowLayout
    getContentPane().setLayout(placeur);//PLacement dans le conteneur de la fenêtre

    ecran = new JLabel(new ImageIcon(nomImage))://Création de la zone d'affichage d'image
    getContentPane().add(ecran);//Ajout de cette zone à la fenêtre

    pack();//taille de fenêtre minimale
    setVIsible(true);//Fenêtre visible
}

public static void main(String argv[]){ //Démarrage du programme
    new AffichageImage("Elephant.gif"); //Créatiopn de la fenêtre
    //new AffichageImage("Koala.gif");
}
}