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

public saisieCodeAccesDelai(){
    //gérer le délai de saisie du code.
        //-> Thread (processus parallèle) lunch by constructeur
            //_Progression bar
            //_durée dépenant de la taille
            //_ B
}