// Datei: exception/GeneratorNullException.java
package exception;

/**
 * Wird geworfen, wenn der Report-Generator null ist.
 */
public class GeneratorNullException extends RuntimeException {
    public GeneratorNullException() {
        super("generator darf nicht null sein");
    }
}
