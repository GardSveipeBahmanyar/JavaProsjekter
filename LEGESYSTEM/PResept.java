class PResept extends HvitResept{ //Oppretter subklassen PResept som utvidelse av klassen HvitResept.

    private String farge; //Deklarer en variablen som skal brukes i konstruktøren til klassen.

    PResept(Legemiddel legemiddel, Lege utskrivendelege, Pasient pasient){  //Skriver konstruktøren og bruker spuer() til å arve parametere fra superklassen. 
        super(legemiddel, utskrivendelege, pasient, 3);
        this.farge = "Hvit P-resept"; //Deklarerer parameteren farge.
    }


    // Override for å overskrive den abstrakte metoden i Superklassen. Metoden returnerer informasjonen lagret
    // i parameteren farge. .
    @Override
    public String farge(){
        return farge;
    }

    //Overskriver den abstrakte metoden fra Superklassen og metoden med samme signatur fra HvitResept. For PResept gjelder et fast avslag på 108, men 
    // det er ikke mulig å betale mindre enn 0. Metoden returner prisen på respeten etter at rabatten er trukket. Hvis prisen er lik rabatten eller mindre
    // returnerer metoden 0.
    @Override
    public int prisAaBetale(){
        int nyPris = legemiddel.hentPris()-108;
        if(nyPris > 0){
            return nyPris;
        }
        else {
            return 0;
        }
    }
    @Override
    public String hentType() {
        return "p";
    }
       
    @Override
    //samme som super, men uten reit
    public String toString(){
        return hentLegemiddel().hentID() + "," + hentLege() + "," + pasient.hentID() + "," + hentType();
    }

    @Override
    public String enkelToString() {
        return "p-resept paa " + hentLegemiddel().hentNavn();
    }

}