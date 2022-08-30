public class TomListeException extends RuntimeException{
    TomListeException(){
        super("Listen er tom, kan ikke fjerne element. ");
    }
}