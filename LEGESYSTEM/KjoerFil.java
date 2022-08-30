import java.io.FileNotFoundException;

// Dette programmet kan brukes til aa teste Legesystem.java
public class KjoerFil {
    public static void main(String[] args) throws FileNotFoundException, UlovligUtskrift,
            ImposterException, IkkeAutorisasjonUnntak, UgyldigInputUnntak {
        Legesystem legesystem = new Legesystem("testfil2.txt");
    }
}
