

public class Node {
    private int minnestorrelse;
    private int prosessorantall;

    // Oppretter Node-objekt.
    public Node(int ms, int pa) {
        minnestorrelse = ms;
        prosessorantall = pa;
    }

    public int hentProsessorAnt() {
        return prosessorantall;
    }

    public int hentMinnestorrelse() {
        return minnestorrelse;
    }
}
