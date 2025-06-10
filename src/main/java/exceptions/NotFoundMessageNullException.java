// Datei: exception/NotFoundMessageNullException.java
package exceptions;

/**
 * Wird geworfen, wenn die NotFound-Meldung null ist.
 */
public class NotFoundMessageNullException extends RuntimeException {
    public NotFoundMessageNullException() {
        super("notFoundMessage darf nicht null sein");
    }
}
