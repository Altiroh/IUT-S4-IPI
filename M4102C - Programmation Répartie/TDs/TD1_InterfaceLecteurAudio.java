import javax.swing.*;
import javax.awt.*;

public class LecteurAudio extends JFrame
{
    private JLabel titre; //titre
    private JButton play,pause,stop; //bouton de commande
    private JProgressBar avance; //Barre de progression

    public LecteurAudio() //Interface de commande du lecteur audio
    {
        super("Lecteur Audio"); //titre de la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout placeur = new GridLayout(3,1,2,2); // Découpage en trois lignes
        getContentPane().setLayout(placeur); // Placement dans le conteneur de la fenetre

        titre = new.JLabel("Lecteur Audio"); //titre
        titre.setFont(new Font("Serif",Font.BOLD,19)); // Choix font
        titre.setHorizonalAlignment(JLabel.CENTER); //Texte du titre centré
        getContentPane().add(titre); //ajout du titre en haut

        JPanel centre = new JPanel(); // Conteneur pour découper la ligne centrale 
        getContentPane().add(centre); // Conteneur (JPanel) à découper place au centre
        //centre.setLayout(new GridLayout(1,3,2,2)); // Découpage en 3 colonnes égales
        centre.setLayout(new FlowLayout(FlowLayout,LEFT,2,2)); // Placement en flow
        pause = new JButton(new ImageIcon("pause.png"));
        centre.add(pause); // Ajout du bouton pause
        play = new JButton(new ImageIcon("play.png"));
        centre.add(play); // Ajout du bouton play
        stop = new JButton(new ImageIcon("stop.png"));
        centre.add(stop); // Ajout du bouton stop

        avance = new JProgressBar(JProgressBar.HORIZONTAL,0,100); // Barre Horizontal
        avance.setValue(30); // Position initiale de la barre
        getContentPane().add(avance); // Barre de progression en bas

        pack(); //Taille minimale de la fenetre
        setVisible(true); // Fenetre visible
    }

    public static void main(String argv[])
    {
        new LecteurAudio();
    }
}