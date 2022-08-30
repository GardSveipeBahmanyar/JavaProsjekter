public class IteratorTest {
    public static void main(String[] args) {
        
        SortertLenkeliste<Lege> leger = new SortertLenkeliste<>();
        Lege l1 = new Lege("A");
        Lege l2 = new Lege("C");
        Lege l3 = new Lege("B");
        Lege l4 = new Lege("D"); 
        Lege l5 = new Lege("E"); 
        Lege l6 = new Lege("F"); 
        leger.leggTil(l1); 
        leger.leggTil(l2);
        leger.leggTil(l3); 
        leger.leggTil(l4);
        leger.leggTil(l5);
        leger.leggTil(l6);

        Pasient p1 = new Pasient("A", "fnr");
        Pasient p2 = new Pasient("B", "fnr");
        Pasient p3 = new Pasient("C", "fnr");
        Lenkeliste<Pasient> pasienter = new Lenkeliste<>();
        pasienter.leggTil(p1);
        pasienter.leggTil(p2);
        pasienter.leggTil(p3);

        

        for (Pasient p : pasienter) {
            System.out.println(p.toString());
        }
        System.out.println("FERDIG MED FOR-EACH LOOP");

        for (Lege l : leger) {
            System.out.println(l.toString());
        }
        System.out.println("FERDIG MED FOR-EACH LOOP");
        

       // System.out.println(pasienter);
       // System.out.println(leger);

        
    }
}
