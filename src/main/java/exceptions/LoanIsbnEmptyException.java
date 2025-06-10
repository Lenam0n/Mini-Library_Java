package exceptions;

import interfaces.execptions.ILoanException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn die ISBN null oder leer ist.
*/
public class LoanIsbnEmptyException extends RuntimeException implements ILoanException, IValidationException {
 public LoanIsbnEmptyException() {
     super("ISBN darf nicht null/leer sein");
 }
}
