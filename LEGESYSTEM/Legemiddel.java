 abstract public class Legemiddel{ 

    protected String navn; 
    private static int iD = 0;
    private int minID;
    protected int pris;
    protected double mgAvVirkestoff; 

    Legemiddel(String navn, int pris, double mgAvVirkestoff){ //Deklarer konstruktøren med parametre
        this.navn = navn; 
        this.pris = pris;
        this.mgAvVirkestoff = mgAvVirkestoff; 
        this.minID = iD; 
        iD ++;
    }
    //Metoden returnener minID som er den unike heltalls-ID tildelt hvert instanse av klassen Legemiddel og dets subklasser.
    public int hentID(){
        return minID;
    }

    public String hentNavn(){
        return navn;
    }

    public int hentPris(){
        return pris; 
    }

    public double hentVirkestoff(){
        return mgAvVirkestoff;
    }

    public void settNyPris(int pris){
        this.pris = pris; 
    }

    //(navn,type,pris,virkestoff,[styrke])
    public String toString(){
       return (hentNavn() + "," + hentType() + "," + hentPris() + "," + Math.round(hentVirkestoff()));
    }

    abstract String hentType();

}