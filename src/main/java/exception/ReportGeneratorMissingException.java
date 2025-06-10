package exception;

/**
 * Wird geworfen, wenn kein Report-Generator vorhanden ist.
 */
public class ReportGeneratorMissingException extends RuntimeException {
    public ReportGeneratorMissingException() {
        super("Kein Report-Generator verfügbar");
    }
}
