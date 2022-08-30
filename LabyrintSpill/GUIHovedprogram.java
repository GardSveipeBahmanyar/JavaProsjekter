
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;

//Bruker velger labyrint-fil fra sine filer og lager et labyrintobjekt med dette filnavnet
//Forutsetter at det er en laybrintfil.
//Lager en jFrame som labyrint-objektet legger til elementer i
//Pakker sammen elementene labyrint sin initGUI metode lager
//Gjør jFramen synlig tilslutt
class GUIHovedprogram {
    public static void main(String[] args) {
        JFileChooser velg = new JFileChooser();
        int valgt = velg.showOpenDialog(null);
        if(valgt != JFileChooser.APPROVE_OPTION){
            System.exit(1);
        }
        File fil = velg.getSelectedFile();
        Labyrint l = null;
        try {
            l = new Labyrint(fil);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(1);
        }
        JFrame vindu = new JFrame("Labyrint-Solver");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        l.initGUI();
        vindu.add(l);

        vindu.pack();
        vindu.setVisible(true);
    }
}