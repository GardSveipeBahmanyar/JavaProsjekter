class Lege implements Comparable<Lege> {

    protected String navn;
    protected Lenkeliste<Resept> utskrevedeResepter;

    Lege(String navn){
        this.navn = navn;
        utskrevedeResepter = new Lenkeliste<>();
    }
    
    public String hentNavn(){
        return navn;
    }

    //Denne metoden trenger ikke å befinne seg i klassen, men vi kan da sjekke om instansen av Lege-klassen har en kontrollID.
    public String hentKontrollID(){
        String kontrollID = null;
        return kontrollID;
    }
    
    //(navn,kontrollid / 0 hvis vanlig lege)
    public String toString(){
        return navn + ",0";
    }

    @Override
    public int compareTo(Lege lege){
       String a = navn; 
       String b = lege.hentNavn();
       
        return a.compareTo(b);
    }

    public void leggTilResept(Resept resept){
        utskrevedeResepter.leggTil(resept);
    }

    public String hentResepter(){
        String listeString = "Liste av resepter: ";
        for(int i = 0; i < utskrevedeResepter.stoerrelse(); i++){
            listeString += "\n" + utskrevedeResepter.hent(i);
        }
        return listeString; 
    }

    //Metoder som oppretter resepter:

    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if(legemiddel instanceof Narkotisk){
            if(this instanceof Spesialist){
                HvitResept hr = new HvitResept(legemiddel, this, pasient, reit);
                utskrevedeResepter.leggTil(hr);
                pasient.leggTilResept(hr);
                return hr;
            }
            else{
                throw new UlovligUtskrift(this, legemiddel);
            }
        }
        else{
            HvitResept hr = new HvitResept(legemiddel, this, pasient, reit);
            utskrevedeResepter.leggTil(hr);
            pasient.leggTilResept(hr);
            return hr;
        }
    }


    public MilitaerResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if(legemiddel instanceof Narkotisk){
            if(this instanceof Spesialist){ //Eller "har godkjenningsfritak"
            MilitaerResept mr = new MilitaerResept(legemiddel, this, pasient, reit);
            utskrevedeResepter.leggTil(mr);
            pasient.leggTilResept(mr);
            return mr; 
        }
        else{
            throw new UlovligUtskrift(this, legemiddel);}
        }
        else{
            MilitaerResept mr = new MilitaerResept(legemiddel, this, pasient, reit);
            utskrevedeResepter.leggTil(mr);
            pasient.leggTilResept(mr);
            return mr; 
        }
    }


    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift{
        if(legemiddel instanceof Narkotisk){
            if(this instanceof Spesialist){ //Eller "har godkjenningsfritak"
            PResept pr = new PResept(legemiddel, this, pasient);
            utskrevedeResepter.leggTil(pr);
            pasient.leggTilResept(pr);
            return pr;
        }
        else{
            throw new UlovligUtskrift(this, legemiddel);}
        }
        else{
            PResept pr = new PResept(legemiddel, this, pasient);
            utskrevedeResepter.leggTil(pr);
            pasient.leggTilResept(pr);
            return pr;
        }
    }   

    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if(legemiddel instanceof Narkotisk){
            if(this instanceof Spesialist){ 
            BlaaResept br = new BlaaResept(legemiddel, this, pasient, reit);
            utskrevedeResepter.leggTil(br);
            pasient.leggTilResept(br);
            return br;
        }
        else{
            throw new UlovligUtskrift(this, legemiddel);}
        }
        else{
            BlaaResept br = new BlaaResept(legemiddel, this, pasient, reit);
            utskrevedeResepter.leggTil(br);
            pasient.leggTilResept(br);
            return br;
        }
    }

    public int antNarkotiskUtskrevet() {
        int antall = 0;
        for (Resept resept : utskrevedeResepter) {
            if (resept.hentLegemiddel() instanceof Narkotisk) {
                antall++;
            }
        }
        return antall;
    }
}