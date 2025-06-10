package exception;

/**
 * Wird geworfen, wenn die übergebene Item-Liste für den Report null ist.
 */
public class ReportItemsNullException extends RuntimeException {
    public ReportItemsNullException(String title) {
        super("Item-Liste für Report '" + title + "' ist null");
    }
}
