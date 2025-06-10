package exception;

import interfaces.execptions.IBookException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn der Titel null oder leer ist.
*/
public class BookTitleEmptyException extends RuntimeException implements IBookException, IValidationException {
 public BookTitleEmptyException() {
     super("Titel darf nicht null/leer sein");
 }
}
