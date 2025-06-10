package exception;

import interfaces.execptions.IBookException;
import interfaces.execptions.IValidationException;

/**
* Spezielle RuntimeException, wenn ein Buch nicht gefunden wird.
*/
public class BookNotFoundException extends RuntimeException implements IBookException, IValidationException {
 public BookNotFoundException(String message) {
     super(message);
 }
}
