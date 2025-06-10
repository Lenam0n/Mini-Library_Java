package exception;

import interfaces.IMemberException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn die E-Mail null oder nicht den Grundanforderungen entspricht.
*/
public class MemberEmailInvalidException extends RuntimeException implements IMemberException, IValidationException {
 public MemberEmailInvalidException(String email) {
     super("Ungültige E-Mail: " + email);
 }
}
