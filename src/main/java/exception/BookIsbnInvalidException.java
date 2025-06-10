package exception;

import interfaces.IBookException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn die ISBN null oder nicht im Muster "XXX-YYYYYYYYYY" ist.
*/
public class BookIsbnInvalidException extends RuntimeException implements IBookException, IValidationException {
 public BookIsbnInvalidException(String isbn) {
     super("Ungültige ISBN: " + isbn);
 }
}
