import java.util.ArrayList;

public class Aapning extends HvitRute{
    String symbol;
    int rad = 0;
    int kolonne = 0;
    Rute Nnabo = null;
    Rute Snabo = null;
    Rute Onabo = null;
    Rute Vnabo = null;
    Aapning(String symbol, int rad, int kolonne, Labyrint lab){
        super(symbol, rad, kolonne, lab);
        this.symbol = symbol;
        this.rad = rad;
        this.kolonne = kolonne;
        
    }
    //Dersom vi kommer til en aapning legge rvi til aapningen i tuppel til veien. Legger til utvei i labyrintens utveiliste.
    //Foretar samme sjekk som på hvitruteobjektene og kaller gaa på alle naboene. Deretter returnerer vi for aapning er slutten på en vei.

    public void gaa(Rute startRute, ArrayList<Tuppel> vei){
        vei.add(new Tuppel(rad, kolonne));
        lab.utVeier.add(vei);
        if(checked){
            return;
        }
        checked = true;
        for(Rute nabo : this.naboer){
            if(nabo != startRute){
            nabo.gaa(this, new ArrayList<Tuppel>(vei));
        }
        }
        checked = false;
        return;
    }
    
}
