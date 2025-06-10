package exceptions;

import interfaces.execptions.IMemberException;
import interfaces.execptions.IValidationException;

/**
* Spezielle RuntimeException, wenn ein Member nicht gefunden wird.
*/
public class MemberNotFoundException extends RuntimeException implements IMemberException, IValidationException {
 public MemberNotFoundException(String message) {
     super(message);
 }
}
