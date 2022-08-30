import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

public class SortRute extends Rute {
    String symbol;
    int rad = 0;
    int kolonne = 0;
    Rute Nnabo = null;
    Rute Snabo = null;
    Rute Onabo = null;
    Rute Vnabo = null;
    Labyrint lab;

    SortRute(String symbol, int rad, int kolonne, Labyrint lab) {
        super(symbol, rad, kolonne, lab);
        this.symbol = symbol;
        this.rad = rad;
        this.kolonne = kolonne;
        this.lab = lab;
    }

    void initGUI() {
        setBackground(Color.black);
        class SortRuteKnapp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ikke en hvit-rute");
            }
        }
        addActionListener(new SortRuteKnapp());
    }

    // Dersom vi kommer til en sortrute, returnerer vi bare.
    public void gaa(Rute startRute, ArrayList<Tuppel> vei) {
        return;
    }

    public char tilTegn() {
        return ('#');
    }
    public void settBakgrunn(){
        setBackground(Color.black);
    }
}
