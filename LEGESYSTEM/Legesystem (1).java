import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

public class Legesystem {

    SortertLenkeliste<Lege> leger;
    Lenkeliste<Resept> resepter;
    Lenkeliste<Legemiddel> legemidler;
    Lenkeliste<Pasient> pasienter;

    public Legesystem(String filnavn) throws ImposterException, UlovligUtskrift, IkkeAutorisasjonUnntak,
            UgyldigInputUnntak, FileNotFoundException {

        leger = new SortertLenkeliste<>();
        resepter = new Lenkeliste<>();
        legemidler = new Lenkeliste<>();
        pasienter = new Lenkeliste<>();

        lesFraFil(filnavn);
        meny();
    }

    public void lesFraFil(String filnavn)
            throws FileNotFoundException, UlovligUtskrift, UgyldigInputUnntak {

        ArrayList<String> feileLinjer = new ArrayList<>(); // liste med linjene med feil fra filen som leses inn
        Scanner sc = new Scanner(new File(filnavn));
        String linje = sc.nextLine();

        while (linje != "") {

            // les inn pasienter fra fil
            if (linje.equals("# Pasienter (navn,fnr)")) {

                linje = sc.nextLine();

                while (!linje.substring(0, 1).equals("#")) {

                    String[] biter = linje.split(",");

                    pasienter.leggTil(new Pasient(biter[0], biter[1]));

                    linje = sc.nextLine();
                }
            }

            // les inn legemiddler fra fil
            else if (linje.equals("# Legemidler (navn,type,pris,virkestoff[,styrke])")) {

                linje = sc.nextLine();

                while (!linje.substring(0, 1).equals("#")) {

                    Legemiddel nyttLegemiddel = null;
                    String[] biter = linje.split(",");
                    String navn, type;
                    int pris, styrke = 0;
                    double virke;
                    String feilLinje = "";

                    // sjekk at legemiddelet er gyldig
                    if (isDouble(biter[2]) && isDouble(biter[3])) {
                        navn = biter[0];
                        type = biter[1];
                        pris = (int) Double.parseDouble(biter[2]);
                        virke = Double.parseDouble(biter[3]);

                        // sjekk om styrke blir oppgit i filen
                        if (biter.length == 5) {
                            if (isInteger(biter[4])) {
                                styrke = Integer.parseInt(biter[4]);
                            } else {
                                feilLinje = linje;
                                feileLinjer.add(feilLinje);
                            }
                        }

                        // hvis linjen er godkjent
                        if (feilLinje.equals("")) {
                            if (type.equals("narkotisk")) {
                                nyttLegemiddel = new Narkotisk(navn, pris, virke, styrke);
                            } else if (type.equals("vanedannende")) {
                                nyttLegemiddel = new Vanedannende(navn, pris, virke, styrke);
                            } else if (type.equals("vanlig")) {
                                nyttLegemiddel = new Vanlig(navn, pris, virke);
                            }
                            legemidler.leggTil(nyttLegemiddel);
                        }
                    } else {
                        feilLinje = linje;
                        feileLinjer.add(feilLinje);
                    }

                    linje = sc.nextLine();
                }
            }

            // Les inn leger fra fil
            else if (linje.equals("# Leger (navn, kontrollid / 0 hvis vanlig lege)")) {

                linje = sc.nextLine();

                while (!linje.substring(0, 1).equals("#")) {
                    Lege nyLege;
                    String[] biter = linje.split(",");
                    String navn = biter[0];
                    String kontrollID = biter[1];

                    if (kontrollID.equals("0")) {
                        nyLege = new Lege(navn);
                    } else {
                        nyLege = new Spesialist(navn, kontrollID);
                    }
                    leger.leggTil(nyLege);

                    linje = sc.nextLine();
                }
            }

            // Les inn resepter fra fil
            else if (linje.equals("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])")) {

                linje = sc.nextLine();

                while (!linje.equals("")) {
                    Legemiddel legemiddel = null;
                    Resept nyResept = null;
                    Lege lege = null;
                    Pasient pasient = null;
                    int reit = 0;

                    String[] biter = linje.split(",");
                    int legemiddelNr = Integer.parseInt(biter[0]);
                    String legeNavn = biter[1];
                    int pasientID = Integer.parseInt(biter[2]);
                    String type = biter[3];

                    // finn riktig legemiddel
                    if (legemiddelNr > legemidler.stoerrelse()) {
                        legemiddel = null;

                    } else {
                        legemiddel = legemidler.hent(legemiddelNr);
                    }
                    // finn riktig lege
                    for (Lege l : leger) {
                        if (l.hentNavn().equals(legeNavn)) {
                            lege = l;
                        }
                    }
                    // finn riktig pasient
                    for (Pasient p : pasienter) {
                        if (p.hentID() == pasientID) {
                            pasient = p;
                        }
                    }
                    // sjekk om reit blir oppgitt i filen
                    if (biter.length == 5) {
                        reit = Integer.parseInt(biter[4]);
                    }

                    // oppretter Resept hvis vi har Legen, Legemiddelet og Pasienten vi trenger
                    if (lege != null && legemiddel != null && pasient != null) {

                        if (type.equals("hvit")) {
                            try {
                                nyResept = lege.skrivHvitResept(legemiddel, pasient, reit);
                            } catch (UlovligUtskrift e) {
                                // System.out.println(e);
                            }
                        } else if (type.equals("blaa")) {
                            try {
                                nyResept = lege.skrivBlaaResept(legemiddel, pasient, reit);
                            } catch (UlovligUtskrift e) {
                                // System.out.println(e);
                            }
                        } else if (type.equals("militaer")) {
                            try {
                                nyResept = lege.skrivMillitaerResept(legemiddel, pasient, reit);
                            } catch (UlovligUtskrift e) {
                                // System.out.println(e);
                            }
                        } else if (type.equals("p")) {
                            try {
                                nyResept = lege.skrivPResept(legemiddel, pasient);
                            } catch (UlovligUtskrift e) {
                                // System.out.println(e);
                            }
                        }
                        if (nyResept != null) {
                            resepter.leggTil(nyResept);
                        }
                    } else {
                        feileLinjer.add(linje);
                    }

                    linje = "";
                    if (sc.hasNextLine()) {
                        linje = sc.nextLine();
                    }
                }
            }
        }
    }

    public void meny() throws ImposterException, UlovligUtskrift, IkkeAutorisasjonUnntak, UgyldigInputUnntak {
        Scanner in = new Scanner(System.in);
        String valg = "";

        while (!valg.equals("a")) {

            skrivHovedMeny();
            valg = in.nextLine().toLowerCase();

            if (valg.equals("1")) {
                skrivLegesystem();
            }

            else if (valg.equals("2")) {
                skrivMenyValg2();
                registrerNoe(in.nextLine().toLowerCase());
            }

            else if (valg.equals("3")) {
                brukResept();
            }

            else if (valg.equals("4")) {
                statistikk();
            }

            else if (valg.equals("5")) {
                skrivTilFil();
            }

            else if (!valg.equals("a")) {
                System.out.println("UgyldigInput:" + valg);
            }
        }
        in.close();
    }

    public void skrivHovedMeny() {

        String tekst = "\n     *** Hovedmeny *** \n\n";
        tekst += "1: Se alle pasienter, leger, legemidler og resepter\n";
        tekst += "2: Registrer ny pasient, lege, legemiddel eller resept\n";
        tekst += "3: Bruk resept\n";
        tekst += "4: Se statistikk\n";
        tekst += "5: Skriv alle data til fil\n";
        tekst += "a: Avslutt\n\n";
        tekst += "[Denne IT-tjenesten leveres av @R-TripleG]\n";
        System.out.println(tekst);
    }

    private void skrivMenyValg2() {

        String tekst = "\nJeg oensker aa legge til \n";
        tekst += "1: Ny pasient\n";
        tekst += "2: Ny lege\n";
        tekst += "3: Nytt legemiddel\n";
        tekst += "4: Ny resept\n\n";
        tekst += "h: Vis hovedmenyen\n";
        System.out.println(tekst);
    }

    private void skrivLegesystem() {

        String tekst = "\nListe over alle pasienter: \n\n";
        for (Pasient pasient : pasienter) {
            tekst += pasient.toString() + "\n";
        }
        tekst += "\nListe over alle legemidler: \n\n";
        for (Legemiddel legemiddel : legemidler) {
            tekst += legemiddel.toString() + "\n";
        }
        tekst += "\nListe over alle leger: \n\n";
        for (Lege lege : leger) {
            tekst += lege.toString() + "\n";
        }
        tekst += "\nListe over alle resepter: \n\n";
        for (Resept resept : resepter) {
            tekst += resept.enkelToString() + "\n";
        }
        System.out.println(tekst);
    }

    private void registrerNoe(String valg)
            throws ImposterException, IkkeAutorisasjonUnntak, UgyldigInputUnntak, UlovligUtskrift {
        Scanner in = new Scanner(System.in);

        while (!valg.equals("h")) {
            
            if (valg.equals("1")) {
                // legg til ny pasient
                Pasient nyPasient ;
                int finnesFraFoer = 0; // 0 betyr false, 1 betyr true

                System.out.println("Oppgi foedselsnummer til pasienten: ");
                String nyFoedselsnummer = in.nextLine();
                for (Pasient p : pasienter) {
                    if (p.hentFoedselsnummer().equals(nyFoedselsnummer) && finnesFraFoer == 0) {
                        finnesFraFoer = 1;
                        System.out.println("Pasienten finnes allerede i systemet. Prov paa nytt ");
                    }
                }
                if (finnesFraFoer == 0) {
                    System.out.println("Oppgi navn paa pasienten: ");
                    String nyttPasientNavn = in.nextLine();
                    nyPasient = new Pasient(nyttPasientNavn, nyFoedselsnummer);
                    pasienter.leggTil(nyPasient);
                    System.out.println("\nNy pasient " + nyttPasientNavn + " ("+nyFoedselsnummer+") registrert ");
                }
                valg = "h";
            }

            else if (valg.equals("2")) {
                // legg til ny lege
                // denne kodeblokken gaar utifra at to leger ikke kan ha likt navn
                Lege nyLege;
                int finnesFraFoer = 0; // 0 betyr false, 1 betyr true
                String navn, kontrollID;

                System.out.println("Er legen spesialist? (ja/nei)");
                String erSpesialist = in.nextLine().toLowerCase();
                System.out.println("Oppgi navn paa legen: ");
                navn = in.nextLine();

                for (Lege lege : leger) {
                    // sjekk om legen finnes fra foer
                    if (lege.hentNavn().toLowerCase().equals(navn.toLowerCase())) {
                        finnesFraFoer = 1;
                    }
                }
                if (finnesFraFoer == 0) {
                    if (erSpesialist.equals("ja")) {
                        System.out.println("Oppgi kontrollID: ");
                        kontrollID = in.nextLine();
                        nyLege = new Spesialist(navn, kontrollID);
                    }
                    else {
                        nyLege = new Lege(navn);
                    }
                    leger.leggTil(nyLege);
                    System.out.println("Ny lege Dr. " + navn + " registrert ");
                }
                else {
                    System.out.println("Dr. " + navn + " er allerede registrert ");
                }
                valg = "h";
            }

            else if (valg.equals("3")) {
                // legg til nytt legemiddel
                Legemiddel nyttLegemiddel = null;
                String navn, type;
                int pris, styrke;
                double virke;
                boolean finnesFraFoer = false;

                System.out.println("\nNavn paa legemiddelet: ");
                navn = in.nextLine();

                // se om legemiddelnavnet finnes fra foer
                for (Legemiddel l : legemidler) {
                    if (navn.toLowerCase().equals(l.hentNavn().toLowerCase())) {
                        finnesFraFoer = true;
                    }
                }
                if (finnesFraFoer) {
                    System.out.println("\nLegemiddel " + navn + " allerede registrert ");
                }
                else {
                    // legemiddelnavnet finnes ikke fra foer
                    System.out.println("\nPris (heltall): ");
                    pris = Integer.parseInt(in.nextLine());
                    System.out.println("\nMengde virkestoff i mg (desimaltall): ");
                    virke = Double.parseDouble(in.nextLine());
                    System.out.println("\nType legemiddel: ");
                    type = in.nextLine().toLowerCase();

                    if (type.equals("vanlig")) {
                        nyttLegemiddel = new Vanlig(navn, pris, virke);
                    }
                    else if (type.equals("narkotisk") || type.equals("vanedannende")) {
                        System.out.print("\nStyrke paa legemiddelet: ");
                        styrke = in.nextInt();
                        if (type.equals("vanedannende")) {
                            nyttLegemiddel = new Vanedannende(navn, pris, virke, styrke);
                        }
                        else if (type.equals("narkotisk")) {
                            nyttLegemiddel = new Narkotisk(navn, pris, virke, styrke);
                        }
                    }
                    if (nyttLegemiddel == null) {
                        System.out.println("\nUgyldig input: " + type);
                    }
                    else {
                        legemidler.leggTil(nyttLegemiddel);
                        System.out.println("\nNytt " + type + " legemiddel " + navn + " registrert ");
                    }
                }
                valg = "h"; // tilbake til hovedmeny
            }

            else if (valg.equals("4")) {
                // legg til ny resept
                Resept nyResept = null;

                System.out.println("\nOppgi resepttype: (eks hvitresept)");
                String reseptType = in.nextLine().toLowerCase();
                // sjekk om reseptype er riktig
                if (! (reseptType.equals("hvitresept") || (reseptType.equals("presept")) || (reseptType.equals("militaerresept")) || (reseptType.equals("blaaresept")))) {
                    // Kanskje ikke det beste unntaket men vi hadde ikke nok tid
                    throw new UgyldigInputUnntak(reseptType);
                }

                System.out.println("\nOppgi pasientens foedselsnr: ");
                String fnr = in.nextLine();
                Pasient pasient = null;
                // sjekk om fodselsnr er riktig
                for (Pasient p : pasienter) {
                    if (p.hentFoedselsnummer().equals(fnr)) {
                        pasient = p;
                        System.out.println("Valgt pasient: " + pasient.hentNavn());
                    }
                }
                if (pasient == null) {
                    // Kanskje ikke det beste unntaket men vi hadde ikke nok tid
                    throw new UgyldigInputUnntak(fnr);
                }
                System.out.println("\nOppgi legemiddelnavn: ");
                String legemiddelNavn = in.nextLine().toLowerCase();
                Legemiddel legemiddel = null;
                // sjekk om legemiddelets navn er riktig
                for (Legemiddel l : legemidler) {
                    if (l.hentNavn().toLowerCase().equals(legemiddelNavn)) {
                        legemiddel = l;
                        System.out.println("Type: " + legemiddel.hentType());
                    }
                }
                if (legemiddel == null) {
                    // Kanskje ikke det beste unntaket men vi hadde ikke nok tid
                    throw new UgyldigInputUnntak(legemiddelNavn);
                }
                System.out.println("\nOppgi reit (ved p-respet, tast 0): ");
                int reit = Integer.parseInt(in.nextLine());

                System.out.println("Oppgi utskrivende leges navn: ");
                String legeNavn = in.nextLine().toLowerCase();
                Lege lege = null;
                // sjekk at legens navn er riktig
                for (Lege l : leger) {
                    if (l.hentNavn().toLowerCase().equals(legeNavn)) {
                        lege = l;
                    }
                }
                if (lege == null) {
                    // unntaket sender ikke brukeren tilbake til hovedmeny, men vi hadde ikke tid
                    throw new ImposterException(legeNavn);
                }

                // Sjekk om legen har lov til aa skrive ut resepten
                if ((legemiddel instanceof Narkotisk && lege instanceof Spesialist) || !(legemiddel instanceof Narkotisk)) {
                    if (reseptType.equals("hvitresept")) {
                        nyResept = lege.skrivHvitResept(legemiddel, pasient, reit);
                    } else if (reseptType.equals("presept")) {
                        nyResept = lege.skrivPResept(legemiddel, pasient);
                    } else if (reseptType.equals("militaerresept")) {
                        nyResept = lege.skrivMillitaerResept(legemiddel, pasient, reit);
                    } else if (reseptType.equals("blaaresept")) {
                        nyResept = lege.skrivBlaaResept(legemiddel, pasient, reit);
                    }
                }
                else {
                    // unntaket sender ikke brukeren tilbake til hovedmeny, men vi hadde ikke tid
                    throw new IkkeAutorisasjonUnntak();
                }
                resepter.leggTil(nyResept);
                System.out.println("\nNy " + nyResept.enkelToString() + " ble registrert ");
            }
            valg = "h";
        }
    }

    private void brukResept() {

        Scanner in = new Scanner(System.in);
        String tekst = "Hvilken pasient vil du se resepter for? (Tast inn pasientens ID)\n\n";
        for (Pasient pasient : pasienter) {
            tekst += pasient.hentID() + ": " + pasient.hentNavn() + " ("+pasient.hentFoedselsnummer()+") \n";
        }
        System.out.println(tekst);

        int pasientID = in.nextInt();
        Pasient pasient = pasienter.hent(pasientID); // valgt pasient
        tekst = "Valgt pasient: " + pasient.hentNavn() + "\n";

        if (!pasient.harResept()) {
            tekst += ".. har ingen registrerte resepter ";
            System.out.println(tekst);
        }

        else {
            tekst += "Hvilken resept vil du bruke? (Tast inn reseptens ID) \n\n";
            tekst += pasient.skrivResepter();
            System.out.println(tekst);

            int reseptID = in.nextInt();
            Resept resept = resepter.hent(reseptID); // valgt resept

            if (resept.bruk()) {
                tekst = "Brukte " + resept.hentLegemiddel().hentNavn() + "-resept, " + resept.hentReit()
                        + " reit igjen ";
                System.out.println(tekst);
            } else {
                System.out.println("Kan ikke bruke resept, ingen reit igjen ");
            }
        }
    }

    private void statistikk() throws UgyldigInputUnntak {

        Scanner in = new Scanner(System.in);
        int antVanedannende = 0;
        int antNarkotisk = 0;

        String tekst = "\nMeny for statistikk\n";
        tekst += "1: Se antall vanedannende resepter utskrevet \n";
        tekst += "2: Se antall narkotiske resepter utskrevet \n";
        tekst += "3: Se statistikk over mulig misbruk av narkotiske resepter\n";
        tekst += "\nh: Vis hovedmenyen \n";
        System.out.println(tekst);

        String valg = in.nextLine();
        if (valg.equals("3")) {
            tekst = "\n# Dr. Fornavn Etternavn : antall narkotiske resepter utskrevet\n";
            for (Lege lege : leger) {
                if (lege.antNarkotiskUtskrevet() > 0) {
                    tekst += "Dr. "+lege.hentNavn() + " : " + lege.antNarkotiskUtskrevet() + "\n";
                }
            }
            tekst += "\n# Pasientnavn :  antall resepter paa narkotisk legemiddel\n";
            for (Pasient pasient : pasienter) {
                if (pasient.antNarkotiskeResepter() > 0) {
                    tekst += pasient.hentNavn() + " : " + pasient.antNarkotiskeResepter() + "\n";
                }
            }
            System.out.println(tekst);

        } else if (valg.equals("1") || valg.equals("2")) {
            for (Resept r : resepter) {
                if (r.hentLegemiddel() instanceof Vanedannende) {
                    antVanedannende++;
                } else if (r.hentLegemiddel() instanceof Narkotisk) {
                    antNarkotisk++;
                }
            }
            if (valg.equals("1")) {
                System.out.println("\nAntall vanedannende resepter utskrevet er " + antVanedannende);
            } else {
                System.out.println("\nAntall narkotiske resepter utskrevet er " + antNarkotisk);
            }
        } else {
            // lar ikke brukeren gaa tilbake til hovedmeny men vi hadde ikke tid
            throw new UgyldigInputUnntak(valg);
        }
    }

    private void skrivTilFil() {
        // Lag en ny fil legesystemUtskrift.java
        try {
            File fil = new File("legesystemUtskrift.txt");
            if (!fil.createNewFile()) {
                System.out.println("Legesystemet har allerede blitt skrevet ut");
            }
        } catch (IOException e) {
            System.out.println("Filen kunne ikke opprettes");
        }

        // Skriv til denne filen
        // Sjekk om den skriver ut i ID-rekkefolge!
        try {
            FileWriter utskrift = new FileWriter("legesystemUtskrift.txt");

            // Skriv ut pasienter
            utskrift.write("# Pasienter (navn, fnr)" + "\n");
            int idTeller = 0;
            // Skriver i rekkefolgen vi leste inn (etter id)
            while (idTeller < pasienter.stoerrelse()) {
                for (Pasient pasient : pasienter) {
                    if (pasient.hentID() == idTeller) {
                        utskrift.write(pasient.toString() + "\n");
                    }
                }
                idTeller++;
            }

            // Skriv ut legemidler
            utskrift.write("# Legemidler (navn,type,pris,virkestoff,[styrke])" + "\n");
            idTeller = 0;
            // Skriver i rekkefolgen vi leste inn (etter id)
            while (idTeller < legemidler.stoerrelse()) {
                for (Legemiddel legemiddel : legemidler) {
                    if (legemiddel.hentID() == idTeller) {
                        utskrift.write(legemiddel.toString() + "\n");
                    }
                }
                idTeller++;
            }

            // Skriv ut leger
            utskrift.write("# Leger (navn,kontrollid / 0 hvis vanlig lege)" + "\n");
            for (Lege lege : leger) {
                utskrift.write(lege.toString() + "\n");
            }

            // Skriv ut resepter
            utskrift.write("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])" + "\n");
            idTeller = 0;
            // Skriver i rekkefolgen vi leste inn (etter id)
            while (idTeller < resepter.stoerrelse()) {
                for (Resept resept : resepter) {
                    if (resept.hentID() == idTeller) {
                        utskrift.write(resept.toString() + "\n");
                    }
                }
                idTeller++;
            }

            utskrift.close();
        } catch (IOException e) {
            System.out.println("Kunne ikke skrive til filen");
        }
    }

    // sjekk om en string er double
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    // sjekk om en string er integer
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
