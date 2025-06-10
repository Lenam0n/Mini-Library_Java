package exception;

import interfaces.ILoanException;
import interfaces.IValidationException;

/**
* Wird geworfen, wenn die ISBN null oder leer ist.
*/
public class LoanIsbnEmptyException extends RuntimeException implements ILoanException, IValidationException {
 public LoanIsbnEmptyException() {
     super("ISBN darf nicht null/leer sein");
 }
}
