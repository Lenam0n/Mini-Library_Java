package exception;

import interfaces.execptions.ILoanException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn das Loan‐Objekt selbst null ist.
*/
public class LoanNullException extends RuntimeException implements ILoanException, IValidationException {
 public LoanNullException() {
     super("Loan darf nicht null sein");
 }
}
