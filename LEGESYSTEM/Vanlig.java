class Vanlig extends Legemiddel{ //Oppretter subklassen Vanlig som en utvidelse av superklassen Legemiddel.

   //Subklassen har ingen nye egenskaper sammenlignet med Superklasen.

    Vanlig(String navn, int pris, double mgAvVirkestoff){ //Oppretter konsturkør med parametere som arves fra superklassen etter bruk av super().
        super(navn, pris, mgAvVirkestoff);
    }

    public String hentType() {
        return "vanlig";
    }
   
}