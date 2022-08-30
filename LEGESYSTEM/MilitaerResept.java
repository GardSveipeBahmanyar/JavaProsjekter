class MilitaerResept extends HvitResept{ //Oppretter subklassen MilitaerResept som utvidelse av klassen HvitResept.

    MilitaerResept(Legemiddel legemiddel, Lege utskrivendelege, Pasient pasient, int reit){ //Skriver konstruktøren og bruker spuer() til å arve parametere fra superklassen. 
        super(legemiddel, utskrivendelege, pasient, reit);
    }

    //Bruker her @Override til å overskrive metoden i Superklassen, og super. for å arve metoden fra superklasen.
    //MilitaerResept har 100 % avslag, det betyr at metoden returnerer 0.
    @Override
    public int prisAaBetale(){
        return (legemiddel.hentPris()*0);
    }
    
    @Override
    public String hentType() {
        return "militaer";
    }

    @Override
    public String enkelToString() {
        return "militaer resept paa " + hentLegemiddel().hentNavn();
    }
}