package exception;

import interfaces.IMemberException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn das Member‐Objekt selbst null ist.
*/
public class MemberNullException extends RuntimeException implements IMemberException,IValidationException {
 public MemberNullException() {
     super("Member darf nicht null sein");
 }
}
