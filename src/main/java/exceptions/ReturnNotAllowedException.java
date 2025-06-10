package exceptions;

import interfaces.execptions.IBookException;
import interfaces.execptions.IMemberException;

/**
 * Wird geworfen, wenn bei der Rückgabe versucht wird,
 * ein Buch zurückzugeben, das nicht an das angegebene Mitglied verliehen ist.
 */
public class ReturnNotAllowedException extends RuntimeException implements IBookException,IMemberException {
    public ReturnNotAllowedException(String isbn, Long memberId) {
        super("Fehler Rückgabe: Buch mit ISBN " 
              + isbn 
              + " ist nicht an Mitglied " 
              + memberId 
              + " verliehen");
    }
}
