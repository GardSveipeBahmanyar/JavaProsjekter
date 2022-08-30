
import java.io.*;
import java.util.*;

public class Dataklynge {

    ArrayList<Rack> rackListe = new ArrayList<Rack>();

    private int antNoder = 0;
    private int minnePrNode = 0;
    private int antProsPrNode = 0;
    private int noderPrRack = 0;

    // Leser av fil og setter første linje til antall noder pr rack.
    // Går deretter gjennom hver linje og setter verdier som skal brukes som
    // parametere i node-objekter.
    // Ettersom at vi vet rekkefølgens betydning i filene og at rekkefølgen er
    // "konstant" kan vi gjøre nettopp dette.
    public Dataklynge(String fil) throws FileNotFoundException {
        File filen = new File(fil);
        Scanner sc = new Scanner(filen);
        String npRacks = sc.nextLine();
        noderPrRack = Integer.parseInt(npRacks);

        while (sc.hasNextLine()) {
            String[] biter = sc.nextLine().split(" ");
            antNoder = Integer.parseInt(biter[0]);
            minnePrNode = Integer.parseInt(biter[1]);
            antProsPrNode = Integer.parseInt(biter[2]);
            for (int i = 0; i < antNoder; i++) {
                genererNyRack(minnePrNode, antProsPrNode);
            }

        }
        sc.close();

    }

    // Traverserer gjennom racksa og sjekker om de er fulle. Om de ikke er fulle
    // legges det til noder helt til racken blir full.
    // Om en rack er full, genereres det en ny rack der nodene blir lagt til.
    public void genererNyRack(int ms, int pa) {
        boolean nodeLagtTil = false;
        for (Rack r : rackListe) {
            if (r.noderIListe() < noderPrRack) {
                r.leggTilNode(ms, pa);
                nodeLagtTil = true;
                break;
            }
        }
        if (!nodeLagtTil) {
            Rack nyRack = new Rack();
            nyRack.leggTilNode(ms, pa);
            rackListe.add(nyRack);

        }
    }

    // Traverserer gjennom racklistene og nodene i dem, og teller antall prosessorer
    // med metode fra node-klassen.
    public int antProsessorer() {
        int prosAnt = 0;
        for (Rack r : rackListe) {
            for (Node n : r.noder) {
                prosAnt += n.hentProsessorAnt();
            }
        }

        return prosAnt;
    }

    // Traverserer gjennom racklistene og nodene i dem, og teller noder med høyere
    // eller lik paakrevd minne ved hjelp av node-klassemetode.
    public int noderMedNokMinne(int paakrevdMinne) {
        int antNoder = 0;
        for (Rack r : rackListe) {
            for (Node n : r.noder) {
                if (n.hentMinnestorrelse() >= paakrevdMinne) {
                    antNoder++;
                }
            }
        }
        return antNoder;
    }

    // Returnerer størrelsen på rack-arrayet.
    public int hentAntRacks() {
        int antRacks = rackListe.size();
        return antRacks;
    }
}
