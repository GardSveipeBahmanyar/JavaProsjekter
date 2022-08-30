import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Rute extends JButton {
    String symbol;
    int rad = 0;
    int kolonne = 0;
    Rute Nnabo = null;
    Rute Snabo = null;
    Rute Onabo = null;
    Rute Vnabo = null;
    Labyrint lab;
    ArrayList<Rute> naboer = new ArrayList<>();

    Rute(String symbol, int rad, int kolonne, Labyrint lab) {
        this.symbol = symbol;
        this.rad = rad;
        this.kolonne = kolonne;
        setPreferredSize(new Dimension(100, 100));
        setFont(new Font("Monospaced", Font.BOLD, 40));
    }


    void initGUI() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setText(symbol);
    }

    public abstract char tilTegn();

    public void skrivUtPos() {
        System.out.println("Rad: " + rad);
        System.out.println("Kolonne: " + kolonne);
    }

    public abstract void gaa(Rute start, ArrayList<Tuppel> vei);

    public void finnUtVei() {
        gaa(null, new ArrayList<Tuppel>());
    }

    // Skriver ut naboer dersom de finnes
    public void skrivUtNaboer() {
        if (Nnabo != null) {
            System.out.println("Nnabo: " + Nnabo);
        } else {
            System.out.println("Ingen Nnabo");
        }
        if (Snabo != null) {
            System.out.println("Snabo: " + Snabo);
        } else {
            System.out.println("Ingen Snabo");
        }
        if (Vnabo != null) {
            System.out.println("Vnabo: " + Vnabo);
        } else {
            System.out.println("Ingen Vnabo");
        }
        if (Onabo != null) {
            System.out.println("Vnabo: " + Onabo);
        } else {
            System.out.println("Ingen Onabo");
        }
    }
    public abstract void settBakgrunn();

}
