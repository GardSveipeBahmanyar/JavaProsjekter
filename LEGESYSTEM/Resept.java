abstract public class Resept{ //Oppretter superklassen Resept som en abstrakt klasse, slik at det ikke skal være mulig å opprette instanser av den. 

    protected Legemiddel legemiddel; //Deklarerer variabler som skal brukes i klassens konstruktør som parametere. Merk at legemiddel referer til objekt av klassen Legemiddel og Lege 
    protected Lege lege;  //referer til en instans av Lege.
    private static int iD = 0; 
    private int minID;        
    protected Pasient pasient; 
    protected int reit;

    Resept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reit){ //Oppretter klassens kontruktør med parametere.
        this.legemiddel = legemiddel;
        this.lege = lege; 
        this.pasient = pasient; 
        minID = iD;
        iD ++;
        this.reit = reit;  
    }
 
    //Metoden returnerer en helltalls-ID som er unik for hver objekt av klassen.
    public int hentID(){
        return minID;
    }

    //Metoden returnerer legemiddel parameteren. Merk at ettersom vi har overskrevet metoden toString()
    // i klassen Legemiddel, vil objektet returneres som en streng.
    public Legemiddel hentLegemiddel(){
        return legemiddel;
    }
    
    //Metoden returner uskrivendeLege som er en instanse av klassen Lege. Ettersom metoden toString() også
    // er overskrevet i klassen Lege, returneres denne som en streng også.
    public Lege hentLege(){
        return lege;
    }

    public int hentPasientID() {
        return pasient.hentID();
    }

    //Metoden returnerer tallet reit som står for antallet ganger vi kan bruke resepten.
    public int hentReit(){
        return reit; 
    }
    
    public boolean bruk(){
        if (reit > 0){
            reit --;
            return true;
        }
        return false;
    }

    //Abstrakt metode som ikke kan benyttes som er tom. Denne vil overskrives i subklassene og returnere
    // fargen på resepten.
    abstract public String farge();


    //Dette er en abstrakt klasse, noe som betyr at denne ikke kan brukes. Denne bil overskrives av en metode
    // med samme signatur i subklassene og vil returnere prisen for resepten. 
    abstract public int prisAaBetale();

    abstract String hentType();
 
    //(legemiddelNummer,legeNavn,pasientID,type,[reit])
    //Denne er overskrevet i pResept, hvor den skrives uten reit
    public String toString(){
        return hentLegemiddel().hentID() + "," + hentLege() + "," + hentPasientID() + "," + hentType() + "," + hentReit();
    }

    public String enkelToString(){
        return "resept paa " + hentLegemiddel().hentNavn();
    }
}