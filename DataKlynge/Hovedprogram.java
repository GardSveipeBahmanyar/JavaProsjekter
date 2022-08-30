
import java.io.*;

public class Hovedprogram {
    public static void main(String[] args) throws FileNotFoundException {
        Dataklynge abel = new Dataklynge("dataklynge4.txt");
        System.out.println("-- Noder lagt til i dataklynge: --");
        System.out.println("Noder med minst 32 GB minne: " + abel.noderMedNokMinne(32));
        System.out.println("Noder med minst 64 GB minne: " + abel.noderMedNokMinne(64));
        System.out.println("Noder med minst 128 GB minne: " + abel.noderMedNokMinne(128));
        System.out.println(" ");
        System.out.println("Antall prosessorer tilsammen: " + abel.antProsessorer());
        System.out.print("Antall racks: " + abel.hentAntRacks());
    }
}
