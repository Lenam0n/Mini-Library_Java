package exception;

import interfaces.IMemberException;
import interfaces.IValidationException;

/**
* Spezielle RuntimeException, wenn ein Member nicht gefunden wird.
*/
public class MemberNotFoundException extends RuntimeException implements IMemberException, IValidationException {
 public MemberNotFoundException(String message) {
     super(message);
 }
}
