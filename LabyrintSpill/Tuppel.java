import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tuppel {
    int rad = 0;
    int kolonne = 0;
    Tuppel(int rad, int kolonne){
        this.rad = rad;
        this.kolonne = kolonne;
    }
    public int getRad(){
        return rad;
    }
    public int getKolonne(){
        return kolonne;
    }
    @Override
    public String toString(){
        return "("+rad+","+kolonne+")";
    }
}
