class BlaaResept extends Resept{ //Oppretter subklassen HvitResept som utvidelse av superklassen Resept.

    private String farge; //Deklarer en variablen som skal brukes i konstruktøren til klassen.
    
        BlaaResept(Legemiddel legemiddel, Lege utskrivendelege, Pasient pasient, int reit){ //Skriver konstruktoeren og bruker super() til aa arve parametere fra superklassen. 
            super(legemiddel, utskrivendelege, pasient, reit);
            this.farge = "Blaa";  //Deklarerer parameteren farge.
        }
    
        //Bruker Override for å overskrive den abstrakte metoden i Superklassen. Metoden returnerer informasjonen lagret
        // i parameteren farge. 
        @Override
        public String farge(){
            return farge;
        }
    
        //Begynner med å overskrive den abstrakte metoden i superklassen, og deretter bruker metoden til Legemiddel klassen hentPris(),
        // slik at vi kan returnere prisen for legemiddelet. Det er 75 % rabatt på legemidler med blaaresept.
        @Override
        public int prisAaBetale(){
            int pris = legemiddel.hentPris();
            long pris1 = Math.round(pris*0.25);
            int nyPris = (int) pris1; //må caste nyPris variabelen til int, da Math.round gir variabler av klassen long.
            return nyPris;
        }
    
        @Override
        public String hentType() {
            return "blaa";
        }

        @Override
        public String enkelToString() {
            return "blaa " + super.enkelToString();
        }
    }
    
  