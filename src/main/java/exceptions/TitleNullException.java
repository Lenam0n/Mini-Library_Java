package exceptions;

/**
 * Wird geworfen, wenn der übergebene Titel null ist.
 */
public class TitleNullException extends RuntimeException {
    public TitleNullException() {
        super("Titel darf nicht null sein");
    }
}
