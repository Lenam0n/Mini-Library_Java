package exceptions;

/**
 * Wird geworfen, wenn der erzeugte Report-String null ist.
 */
public class ReportNullException extends RuntimeException {
    public ReportNullException(String title) {
        super("Report '" + title + "' ist null");
    }
}
