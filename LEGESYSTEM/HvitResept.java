class HvitResept extends Resept{ //Oppretter subklassen HvitResept som utvidelse av superklassen Resept.
    
    private String farge; //Deklarer en variablen som skal brukes i konstruktøren til klassen.

    HvitResept(Legemiddel legemiddel, Lege utskrivendelege,Pasient pasient, int reit){ //Skriver konstruktøren og bruker spuer() til å arve parametere fra superklassen. 
        super(legemiddel, utskrivendelege, pasient, reit);
        this.farge = "Hvit";  //Deklarerer parameteren farge.
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //Metoden gjør akkurat det samme som metoden med samme signatur i superklassen Resept.
    @Override
    public int hentID(){
        return super.hentID();
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //Metoden gjør akkurat det samme som metoden med samme signatur i superklassen Resept.
    @Override
    public Legemiddel hentLegemiddel(){
        return super.hentLegemiddel();
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //Metoden gjør akkurat det samme som metoden med samme signatur i superklassen Resept.
    @Override
    public Lege hentLege(){
        return super.hentLege();
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //Metoden gjør akkurat det samme som metoden med samme signatur i superklassen Resept.
    @Override
    public int hentReit(){
        return super.hentReit(); 
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //Metoden gjør akkurat det samme som metoden med samme signatur i superklassen Resept.
    @Override
    public boolean bruk(){
        return super.bruk();
    }  

    //Bruker Override for å overskrive den abstrakte metoden i Superklassen. Metoden returnerer informasjonen lagret
    // i parameteren farge. 
    @Override
    public String farge(){
        return farge;
    }

    //Begynner med å overskrive den abstrakte metoden i superklassen, og deretter bruker metoden til Legemiddel klassen hentPris(),
    // slik at vi kan returnere prisen for legemiddelet.
    @Override
    public int prisAaBetale(){
        return (legemiddel.hentPris());
    }

    @Override
    public String hentType() {
        return "hvit";
    }

    @Override
    public String enkelToString() {
        return "hvit " + super.enkelToString();
    }
}