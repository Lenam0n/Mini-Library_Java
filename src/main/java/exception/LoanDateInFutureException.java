package exception;

import java.time.LocalDate;

import interfaces.execptions.ILoanException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn das Ausleihdatum in der Zukunft liegt.
*/
public class LoanDateInFutureException extends RuntimeException implements ILoanException, IValidationException {
 public LoanDateInFutureException(LocalDate date) {
     super("Ausleihdatum liegt in der Zukunft: " + date);
 }
}
