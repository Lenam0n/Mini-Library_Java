package exception;

import interfaces.IBookException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn der Autor null oder leer ist.
*/
public class BookAuthorEmptyException extends RuntimeException implements IBookException, IValidationException {
 public BookAuthorEmptyException() {
     super("Autor darf nicht null/leer sein");
 }
}
