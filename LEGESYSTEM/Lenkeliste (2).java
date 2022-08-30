
import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {

    class Node {
        T data;
        Node neste;
        Node forrige;

        Node(T data) {
            this.data = data;
            neste = null;
            forrige = null;
        }

        public void settNeste(Node n) { // Trenger en metode for å kunne sette objektet til neste.
            neste = n;
        }

        public T hentNeste() {
            return neste.data;
        }

        public T hentForrige() {
            return forrige.data;
        }

        public T hentData() {
            return data;
        }
    }

    class LenkelisteIterator implements Iterator<T> {
        Node denne;

        LenkelisteIterator() {
            denne = forste;
        }

        @Override
        public boolean hasNext() {
            Node peker = denne;
            if (peker != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            Node peker = denne;
            denne = denne.neste;
            return peker.data;
    }
}

    Node forste = null;
    Node siste = null;

    @Override
    public int stoerrelse() {
        int teller = 0;
        Node peker = forste;
        while (peker != null) {
            teller++;
            peker = peker.neste;
        }
        return teller;
    }

    @Override
    public void leggTil(T data) {

        Node nyNode = new Node(data); // Oppretter ny Node med ønsket ''data''.

        if (stoerrelse() == 0) { // Sjekker om listen er tom.
            forste = nyNode;
            return;
        }

        Node loepNode = forste; // Oppretter en Node med referanse forste, for å iterere over lenkelisten.
        while (loepNode.neste != null) {
            loepNode = loepNode.neste; // Itererer over listen inntil loepNode(forste).neste == null;
        }
        loepNode.neste = nyNode;
        siste = loepNode.neste; // Setter leopNode(forste) sin neste til å være den nye noden.
        nyNode.forrige = loepNode;
    }

    @Override
    public void leggTil(int pos, T data) { // Metoden gjør det mulig å legge inn objekter på ønsket posisjon(indeks).

        int teller = 0; // Trenger en teller, denne hjelper å indeksere gjennom listen.
        Node loepNode = forste; // peker til det forste objektet.
        Node nyNode = new Node(data);

        if (pos == 0) { // Må ta hensyn til pos = 0 som en egen tilfelle. Denne løkken fungerer som en .
            nyNode.neste = forste;
            forste = nyNode;
            loepNode = forste;
            return;
        }

        else if (pos < 0 || pos > stoerrelse() || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        else if (pos == stoerrelse()) {
            leggTil(data);
        } else {
            while (loepNode != null) {

                if (teller == pos - 1 && teller < stoerrelse()) { // Når teller når samme verdi som pos, legger vi inn
                                                                  // det nye objektet.

                    nyNode.neste = loepNode.neste;
                    loepNode.neste = nyNode;
                    nyNode.forrige = loepNode;
                    return;
                }
                teller++;
                loepNode = loepNode.neste;

            }
        }
    }

    @Override
    public void sett(int pos, T data) { // Overskriver elementet på ønsket indeks.
        Node nyNode = new Node(data);
        Node peker = forste;

        if (pos < 0 || pos >= stoerrelse() || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        else {
            for (int i = 0; i < pos; i++) { // sjekk om objektet på gitt indeks posisjon blir endret,
                peker = peker.neste; // eller om vi må endre til pos-1 i løkken.
            }
            peker.data = nyNode.data;
        }
    }

    @Override
    public T hent(int pos) { // Henter dataen lagret i noden plassert på ønsket indeks.
        Node peker = forste;
        int teller = 0;

        if (pos < 0 || pos >= stoerrelse() || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        else {
            if (pos == 0) {
                peker = forste;
            }

            if (pos < stoerrelse()) {
                while (teller != pos) {
                    peker = peker.neste;
                    teller++;
                }
            }

            if (pos == stoerrelse()) {
                while (teller != pos - 1) {
                    peker = peker.neste;
                    teller++;
                }
            }
            return peker.data;
        }
    }

    @Override // Metoden gjør det mulig å fjerne element fra ønsket posisjon.
    public T fjern(int pos) { // har med en Exception for dersom listen er tom.
        int teller = 0;
        Node peker = forste;
        T dataVerdi;

        if (pos >= stoerrelse() || pos < 0 || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        else if (pos == 0) { // Når pos = 0, må det bli tatt hensyn til som en egen tilfelle.
            return fjern(); // Metoden fjernFraPlass må også kunne fjerne forste element i listen.
        }

        else {
            while (peker != null) {
                if (teller == pos - 1) {
                    dataVerdi = peker.neste.data;
                    peker.neste = peker.neste.neste;

                    return dataVerdi;
                }
                peker = peker.neste;
                siste = peker;
                teller++;
            }

            return peker.data;
        }

    }

    @Override
    public T fjern() { // Metoden fjernen det første elementet som ble lagt til i listen. FIFO.
        if (forste == null) {
            throw new UgyldigListeIndeks(0); // Exception for hvis listen er tom.
        }

        T forsteData = forste.data;
        forste = forste.neste;
        return forsteData;
    }

    public T hentNeste(int pos) { // Henter dataen lagret i neste noden i rekken, altså fra pos+1.

        if (pos < 0 || pos > stoerrelse() || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node peker = forste;
        for (int i = 0; i < pos; i++) {
            peker = peker.neste;
        }
        return peker.neste.data;
    }

    public T hentForrige(int pos) { // Henter dataen lagret i noden som ligger før ønsket element.

        if (pos < 0 || pos > stoerrelse() || erTom()) {
            throw new UgyldigListeIndeks(pos);
        }

        Node peker = forste;
        for (int i = 0; i < pos - 1; i++) {
            peker = peker.neste;
        }

        Node forrige = peker;
        return forrige.data;
    }

    @Override
    public String toString() { // Metoden returnerer alle elementers data fra den lenkede listen
        Node peker = forste; // i form av en string.
        String tekst = "";
        while (peker != null) {

            tekst += peker.data + " -> "; // Legger til en pil for å skille hvert element.
            peker = peker.neste;
        }
        return tekst;
    }

    public boolean erTom() {
        if (stoerrelse() == 0) {
            forste = null;
            return true;
        }
        return false;
    }

    public int hentIndeks(T x) {
        Node nyNode = new Node(x);
        Node peker = forste;
        int teller = 0;

        if (erTom()) {
            throw new TomListeException();
        }

        else if (stoerrelse() == 1 || hent(0) == x) {
            teller = 0;
        } else if (teller == stoerrelse()) {
            teller = stoerrelse() - 1;
        }

        else {
            for (int i = 0; teller < stoerrelse() - 1; i++) {

                teller++;
                if (hent(teller) == x) {
                    return teller;
                }
            }
        }
        return teller;
    }

    @Override
    public Iterator<T> iterator() {
        return new LenkelisteIterator();
    }

}