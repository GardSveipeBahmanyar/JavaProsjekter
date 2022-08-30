public class ImposterException extends Throwable {
    ImposterException(String imposter) {
        super("ImposterException: " + imposter + " is an imposter ");
    }
}
