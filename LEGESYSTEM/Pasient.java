public class Pasient{

     private static int ObjID = 0; 
     private int minID;

     protected String navn; 
     protected String foedselsnummer; 

     protected Stabel<Resept> resepter; 

     Pasient(String navn, String foedselsnummer){
         this.navn = navn; 
         this.foedselsnummer = foedselsnummer; 
         minID = ObjID; 
         ObjID ++;
         resepter = new Stabel<>();
     }

    public Stabel<Resept> hentReseptStabel() {
        return resepter;
    }

    public String hentNavn(){
        return navn;
    }

    public String hentFoedselsnummer(){
        return foedselsnummer;
    }

    public int hentID(){
        return minID;
    }

    public void leggTilResept(Resept resept){
        resepter.leggPaa(resept);
    }

    public boolean harResept() {
        return resepter.stoerrelse() > 0;
    }

    // Hent ut hele reseptlisten som string
    public String skrivResepter(){
        String tekst ="";
        for (Resept resept : resepter) {
            tekst += resept.hentID() + ": " + resept.hentLegemiddel().hentNavn() + " ("+resept.hentReit()+" reit) \n";
            // output eksempel: 5: hydrokortison (3 reit)
        }
        return tekst;
    }

    public int antNarkotiskeResepter() {
        int antall = 0;
        for (Resept resept : resepter) {
            if (resept.hentLegemiddel() instanceof Narkotisk) {
                antall++;
            }
        }
        return antall;
    }

    @Override
    //(navn, fnr)
    public String toString(){
        return(hentNavn() + "," + hentFoedselsnummer());
    }
}