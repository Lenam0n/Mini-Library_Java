package exception;

import interfaces.ILoanException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn das Loan‐Objekt selbst null ist.
*/
public class LoanNullException extends RuntimeException implements ILoanException, IValidationException {
 public LoanNullException() {
     super("Loan darf nicht null sein");
 }
}
