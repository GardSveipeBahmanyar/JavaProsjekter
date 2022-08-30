public class IkkeAutorisasjonUnntak extends Throwable {
    IkkeAutorisasjonUnntak() {
        super("Denne legen har ikke autorisasjon til aa skrive ut narkotisk legemiddel ");
    }
}