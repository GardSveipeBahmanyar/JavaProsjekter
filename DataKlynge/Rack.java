
import java.util.ArrayList;

public class Rack {
    ArrayList<Node> noder = new ArrayList<Node>();

    // Legger til node med parameterverdier
    public void leggTilNode(int ms, int pa) {
        noder.add(new Node(ms, pa));
    }

    // Returnerer størrelse på noder-array
    public int noderIListe() {
        int storrelse = noder.size();
        return storrelse;
    }
}
