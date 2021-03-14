import javax.swing.*;
import javax.awt.*;

public class Telephone extends JFrame
{
    private JLabel titre;
    private JButton plus,menu,moins,un,deux,trois,quatre,cinq,
                    six,sept,huit,neuf,etoile,zero,diese;

    public Telephone() //Interface de commande du telephone
    {
        super("Telephone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout placeur = new GridLayout();
        getContentPane().setLayout(placeur);
    }
}