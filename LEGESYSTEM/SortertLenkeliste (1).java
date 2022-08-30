class SortertLenkeliste< T extends Comparable<T>> extends Lenkeliste<T>{
    //Den indre klassen Node arves fra Lenkeliste<T>, og derfor trengs 
    // det ikke å gjøre noe mer. 


    @Override
     public void leggTil(T x){
        Node nyNode = new Node(x);
        
        if(forste == null){
            forste = nyNode;
            return;
        }
        
        else if(forste.data.compareTo(x)>0){
            nyNode.neste = forste;
            forste = nyNode;
            return;
        }
        
        
        Node peker = forste;
        while(peker.neste != null && peker.neste.data.compareTo(x) <= 0){
            peker = peker.neste;
        }
            
            nyNode.neste =  peker.neste;
            nyNode.forrige = peker;
            peker.neste = nyNode;   
            siste = nyNode;  
        }
       
    @Override
    public void leggTil(int pos, T data) { 
        throw new UnsupportedOperationException();
    }

    @Override
    public void sett(int pos, T data){
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int pos) throws UgyldigListeIndeks{
        return super.hent(pos);
    }

    @Override
    public T fjern() {
        if(forste == null){
            throw new UgyldigListeIndeks(0);
        }
        if(stoerrelse() == 1){
            return super.fjern();
        }
        else{
            return super.fjern(stoerrelse() -1);
        }
    }
    
    
}

            
 
