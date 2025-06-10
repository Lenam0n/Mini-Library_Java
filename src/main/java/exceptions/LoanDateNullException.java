package exceptions;

import interfaces.execptions.ILoanException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn das Ausleihdatum null ist.
*/
public class LoanDateNullException extends RuntimeException implements ILoanException, IValidationException {
 public LoanDateNullException() {
     super("Ausleihdatum darf nicht null sein");
 }
}
