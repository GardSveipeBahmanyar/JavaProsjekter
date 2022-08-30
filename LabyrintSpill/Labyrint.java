import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;

public class Labyrint extends JPanel {
    // Properties til labyring-brett
    int radBrett = 0;
    int kolonneBrett = 0;

    ArrayList<ArrayList<String>> brett = new ArrayList<>();
    ArrayList<ArrayList<Rute>> ruteBrett = new ArrayList<>();
    ArrayList<ArrayList<Tuppel>> utVeier = new ArrayList<>();
    ArrayList<ArrayList<Tuppel>> knappUtveier = new ArrayList<>();

    Scanner sc;
    JLabel info;
    int nesteIndex = 1;
    int raskesteVeiIndex = 0;

    Labyrint(File fil) throws FileNotFoundException {
        sc = new Scanner(fil);
        lesInn();
        lagRuteObjekter();
        tildelNaboer();
        
    }
    
    //Når man klikker på "vis neste utvei" knappen vil den vise neste utvei utifra hvilken index raskesteVei har i 
    //Utveierlisten. Vil returnere dersom det ikke er klikket på en hvit
    class visNesteKnapp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(knappUtveier.size() == 0){
                return;
            }
            if(raskesteVeiIndex == 0){
                visNesteUtvei(nesteIndex, knappUtveier);
                nesteIndex++;
            }
            else{
                if(nesteIndex == knappUtveier.size()-1){
                    nesteIndex = 0;
                    visNesteUtvei(nesteIndex, knappUtveier);
                }
                else{
                    visNesteUtvei(nesteIndex, knappUtveier);
                    nesteIndex++;
                }
            }
    }
    }
    //Lager labyrintbrett og tekstbokser.Alt legges inn i en GridBagLayout.
    void initGUI() {
        setLayout(new GridBagLayout());
        JPanel skriftBokser = new JPanel();
        skriftBokser.setLayout(new GridLayout(3, 1));
        skriftBokser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        skriftBokser.setPreferredSize(new Dimension(700, 200));
        skriftBokser.setBackground(Color.white);
        skriftBokser.setForeground(Color.black);
        JLabel overSkrift = new JLabel("LABYRINT-SOLVER 2.0");
        overSkrift.setFont(new Font("Monospaced", Font.BOLD, 20));
        info = new JLabel("Klikk på en hvit rute for å finne den raskeste utveien... ");
        info.setFont(new Font("Monospaced", Font.BOLD, 20));
        JButton nesteKnapp = new JButton("---- Vis neste utvei ----");
        nesteKnapp.addActionListener(new visNesteKnapp());
        nesteKnapp.setFont(new Font("Monospaced", Font.BOLD, 20));
        skriftBokser.add(overSkrift);
        skriftBokser.add(info);
        skriftBokser.add(nesteKnapp);

        JPanel labyrintPanel = new JPanel();
        labyrintPanel.setLayout(new GridLayout(radBrett, kolonneBrett));
        labyrintPanel.setPreferredSize(new Dimension(800, 800));
        add(skriftBokser);
        add(labyrintPanel);
        
        //lager ruteKnappene til labyrinten
        for (ArrayList<Rute> a : ruteBrett) {
            for (Rute b : a) {
                b.initGUI();
                labyrintPanel.add(b);
                new JButton(b.symbol);
            }
        }
    }

    // Oppretter brett
    public void lesInn() {
        String[] dimensjon = sc.nextLine().split(" ");
        radBrett = Integer.parseInt(dimensjon[0]);
        kolonneBrett = Integer.parseInt(dimensjon[1]);
        while (sc.hasNextLine()) {
            ArrayList<String> rader = new ArrayList<>();
            String[] symbol = sc.nextLine().split("");
            for (int i = 0; i < symbol.length; i++) {
                rader.add(symbol[i]);
            }
            brett.add(rader);
        }
    }

    //Kaller på restartMetoden for å resette fargene som vises på labyrinten og viser neste utvei med index nesteindex fra arraylisten vi sender som parameter
    public void visNesteUtvei(int index, ArrayList<ArrayList<Tuppel>> liste){
        if(index < liste.size()){
            restart();
            visVei(liste.get(nesteIndex));
        }
        else{
            return;
        }
    }

    // Returnerer mulige utveier fra posisjon ved å kalle på den rekursive metoden i
    // Rute fra en bestemt posisjon.
    // Finner også den raskeste utveien fra den gitte posisjonen
    // Viser deretter den raskeste utveien fra den ruten bruker har klikket på
    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(int rad, int kol) {
        nesteIndex = 1;
        knappUtveier.clear();
        ArrayList<Tuppel> vei = new ArrayList<>();
        Rute r = hentRute(rad, kol);
        r.gaa(r, vei);
        ArrayList<ArrayList<Tuppel>> utVeiNy = new ArrayList<>(utVeier);
        knappUtveier = new ArrayList<>(utVeier);
        visVei(finnRaskesteUtvei(utVeier));
        info.setText("Antall mulige løsninger funnet: " + utVeier.size());
        utVeier.clear();
        return utVeiNy;
    }

    // Vis raskeste vei istedet for alle. Vis som indikator på skjermen hvor mange
    // utveier det finnes.
    public void visVei(ArrayList<Tuppel> utVeiNy) {
        for (Tuppel t : utVeiNy) {
            for (ArrayList<Rute> a : ruteBrett) {
                for (Rute b : a) {
                    if (b.rad == t.rad && b.kolonne == t.kolonne) {
                        b.setBackground(Color.GREEN);
                    }
                }
            }
        }
    }

    //Resetter fargene som viser den gjeldende løsningen
    public void restart() {
        for (ArrayList<Rute> a : ruteBrett) {
            for (Rute b : a) {
                b.settBakgrunn();
            }
        }
    }

    // Går igjennom brettet(labyrinten) og oppretter Aapning-, SortRute og HvitRute
    // objekter utifra Symbol og plassering på brettet.
    public void lagRuteObjekter() {
        int radIndex = 0;
        int kolonneIndex = 0;
        for (ArrayList<String> a : brett) {
            ArrayList<Rute> rader = new ArrayList<>();
            kolonneIndex = 0;
            for (String b : a) {
                if (b.equals("#")) {
                    rader.add(new SortRute("#", radIndex, kolonneIndex, this));
                } else {
                    if (radIndex == radBrett - 1 || kolonneIndex == kolonneBrett - 1 || radIndex == 0
                            || kolonneIndex == 0) {
                        rader.add(new Aapning(".", radIndex, kolonneIndex, this));
                    } else {
                        rader.add(new HvitRute(".", radIndex, kolonneIndex, this));
                    }

                }

                kolonneIndex++;
            }
            ruteBrett.add(rader);
            radIndex++;
        }
    }

    // Går igjennom brettet og tildeler naboer til alle Rute-objektene. Deretter
    // legger vi dem til i et arraylist.
    // Fjerner alle null-elementene, slik at dersom en rute ikke har f.eks. en
    // Nnabo, vil ikke dette bli tatt med
    // I nabo-arraylisten. Får brukt for denne listen senere da vi skal kalle gaa på
    // alle naborutene.
    public void tildelNaboer() {
        int radIndex = 0;
        int kolonneIndex = 0;
        for (ArrayList<Rute> a : ruteBrett) {
            kolonneIndex = 0;
            for (Rute b : a) {
                b.Nnabo = hentNnabo(radIndex, kolonneIndex);
                b.Snabo = hentSnabo(radIndex, kolonneIndex);
                b.Vnabo = hentVnabo(radIndex, kolonneIndex);
                b.Onabo = hentOnabo(radIndex, kolonneIndex);
                b.naboer.add(b.Nnabo);
                b.naboer.add(b.Snabo);
                b.naboer.add(b.Vnabo);
                b.naboer.add(b.Onabo);
                b.naboer.removeAll(Collections.singleton(null));
                kolonneIndex++;
            }
            radIndex++;
        }
    }

    // Skriver ut brett for bruker
    public void skrivUtBrett() {
        for (ArrayList<String> a : brett) {
            System.out.println(" ");
            for (String b : a) {
                System.out.print(" " + b + " ");
            }
            System.out.println(" ");
        }
    }

    public int hentRad() {
        return radBrett;
    }

    public int hentKolonne() {
        return kolonneBrett;
    }

    // Henter ruteobjekt i gitt posisjon
    public Rute hentRute(int posR, int posK) {
        Rute rute;
        ArrayList<Rute> rutesRad = ruteBrett.get(posR);
        rute = rutesRad.get(posK);
        return rute;
    }

    // Metodene under henter Naboer til Rute-objekter
    Rute hentNnabo(int posR, int posK) {
        Rute Nnabo = null;
        if (posR == 0) {
            // Ingen Nnabo
            // System.out.println("//Ingen Nnabo");
        } else {
            ArrayList<Rute> overRad = ruteBrett.get(posR - 1);
            // Ingen Snabo
            Nnabo = overRad.get(posK);
        }

        return Nnabo;
    }

    Rute hentSnabo(int posR, int posK) {
        Rute Snabo = null;

        if (posR == radBrett - 1) {
            // System.out.println("//Ingen Snabo");
        } else {
            ArrayList<Rute> underRad = ruteBrett.get(posR + 1);
            Snabo = underRad.get(posK);
        }
        return Snabo;

    }

    Rute hentVnabo(int posR, int posK) {
        Rute Vnabo = null;
        ArrayList<Rute> sammeRad = ruteBrett.get(posR);
        if (posK == 0) {
            // System.out.println("//Ingen Vnabo");
        } else {
            Vnabo = sammeRad.get(posK - 1);
        }
        return Vnabo;

    }

    Rute hentOnabo(int posR, int posK) {
        Rute Onabo = null;
        ArrayList<Rute> sammeRad = ruteBrett.get(posR);
        if (posK == kolonneBrett - 1) {
            // System.out.println("//Ingen Onabo");
        } else {
            Onabo = sammeRad.get(posK + 1);
        }
        return Onabo;
    }


    //Finner raskeste utvei fra utveier-arraylisten og setter raskesteVeiIndex til den indexen den raskeste veien har veierlisten.
    ArrayList<Tuppel> finnRaskesteUtvei(ArrayList<ArrayList<Tuppel>> veier) {
        raskesteVeiIndex = 0;
        int nr = 0;
        int sto = Integer.MAX_VALUE;
        ArrayList<Tuppel> raskesteVei = new ArrayList<>();
        if (veier.size() != 0) {
            for (ArrayList a : veier) {
                if (a.size() <= sto) {
                    sto = a.size();
                    nr = veier.indexOf(a);
                }
            }
            raskesteVei = veier.get(nr);
            raskesteVeiIndex = nr;
        }
        return raskesteVei;

    }
}
