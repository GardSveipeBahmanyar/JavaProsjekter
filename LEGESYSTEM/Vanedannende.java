class Vanedannende extends Legemiddel{ //Oppretter subklassen Vanedannende som en utvidelse av superklassen Legemiddel.


    private int styrke;  //Oppretter ny variabel for bruk i subklassens konstruktør 
  

    Vanedannende(String navn, int pris, double mgAvVirkestoff, int styrke){  //Oppretter konstkruktøren til subklassen. 
        super(navn, pris, mgAvVirkestoff);  //bruker metoden super() for å arve parameterne fra superklassen Legemiddel.
        this.styrke = styrke;  //Definerer subklassens egen parameter. 
    }
    
    //Metoden returnerer et heltall som representerer et mål på legemiddelets narkotiske styrke.
    public int hentVanedannendeStyrke(){
        return styrke;
    }

    //Overskriver metoden fra superklassen. 
    @Override
    public String toString(){
        return super.toString() + "," + hentVanedannendeStyrke();
    }

    public String hentType() {
        return "vanedannende";
    }
}

