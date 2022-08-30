

class Stabel<T> extends Lenkeliste<T>{

    //Den indre klassen node arved fra klassen Lenkeliste<T>.
    public void leggPaa(T data){
        leggTil(data);
    }

    public T taAv() throws TomListeException{
        return fjern(stoerrelse()-1);
}
    
    
    public void koblStabler(Stabel<T> nyListe){ //Metoden kan benyttes til aa sette sammen to lister til en.
        if(nyListe == this){
            return; 
        }

        if(forste == null){
            forste = nyListe.forste;
        }

        Node peker = forste; 
        while(peker.neste != null){
            peker = peker.neste; 
        }
    
    peker.neste = nyListe.forste; 
    }
}

