package exception;

/**
 * Wird geworfen, wenn versucht wird, ein Buch auszuleihen,
 * das bereits verliehen ist.
 */
public class BorrowNotAllowedException extends RuntimeException {
    public BorrowNotAllowedException(String isbn) {
        super("Fehler Ausleihung: Buch mit ISBN " 
              + isbn 
              + " ist bereits verliehen");
    }
}
