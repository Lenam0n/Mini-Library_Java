package exceptions;

import interfaces.execptions.IMemberException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn der Name null oder leer ist.
*/
public class MemberNameEmptyException extends RuntimeException implements IMemberException, IValidationException {
 public MemberNameEmptyException() {
     super("Name darf nicht null/leer sein");
 }
}
