class Narkotisk extends Legemiddel{ //Oppretter subklassen Narkotisk som en utvidelse av superklassen Legemiddel.

    private int styrke;  //Oppretter ny variabel for bruk i subklassens konstruktør 
   

    Narkotisk(String navn, int pris, double mgAvVirkestoff, int styrke){ //Oppretter konstkruktøren til subklassen. 
        super(navn, pris, mgAvVirkestoff);  //bruker metoden super() for å arve parameterne fra superklassen Legemiddel.
        this.styrke = styrke; //Definerer subklassens egen parameter. 
    }

    //Metoden returnerer et heltall som representerer et mål på legemiddelets narkotiske styrke.
    public int hentNarkotiskStyrke(){
        return styrke;
    }

    //Overskriver metoden fra superklassen. 
    @Override 
    public String toString(){ //Bruker her super. for å hente virkningen av metoden fra Legemiddel-klassen.
        return super.toString() + "," + hentNarkotiskStyrke();
    }

    public String hentType() {
        return "narkotisk";
    }

}

