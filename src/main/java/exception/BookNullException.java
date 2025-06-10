package exception;

import interfaces.execptions.IBookException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn das Book‐Objekt selbst null ist.
*/
public class BookNullException extends RuntimeException implements IBookException, IValidationException {
 public BookNullException() {
     super("Book darf nicht null sein");
 }
}
