import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

public class HvitRute extends Rute {
    String symbol;
    int rad = 0;
    int kolonne = 0;
    Rute Nnabo = null;
    Rute Snabo = null;
    Rute Onabo = null;
    Rute Vnabo = null;
    Labyrint lab;
    boolean checked = false;

    HvitRute(String symbol, int rad, int kolonne, Labyrint lab) {
        super(symbol, rad, kolonne, lab);
        this.symbol = symbol;
        this.rad = rad;
        this.kolonne = kolonne;
        this.lab = lab;
    }

    //Etter at initGUI metoden til Hvit-rute er kalt, blir det generert en knapp som HvitRuteKnapp-objekt. 
    //Klikkes ruten på vil alltid labyrint-objektets restart metode kalles før den kaller på finnUtveiFra
    //Bruker arv slik at initGUI også gjelder for aapningsobjektene.
    void initGUI() {
        setBackground(Color.white);
        class HvitRuteKnapp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                lab.restart();
                lab.finnUtveiFra(rad, kolonne);
            }
        }
        addActionListener(new HvitRuteKnapp());
    }

    // Sjekker om ruten allerede er checked. Om den er returnerer vi, om den ikke er
    // adder en ny tuppel til den veien som ble sendt som parameter.
    // Setter checked til true, og går gjennom alle naboene til ruten. Dersom en av
    // naboene ikke er startrute noden,
    // Bruker vi gaametoden på de naboene og sender med en kopi av veien hittil.
    public void gaa(Rute startRute, ArrayList<Tuppel> vei) {
        if (checked) {
            return;
        }
        vei.add(new Tuppel(rad, kolonne));
        checked = true;
        for (Rute nabo : this.naboer) {
            if (nabo != startRute) {
                nabo.gaa(this, new ArrayList<Tuppel>(vei));
            }
        }
        checked = false;
    }

    public char tilTegn() {
        return ('.');
    }

    public void settBakgrunn(){
        setBackground(Color.white);
    }
}