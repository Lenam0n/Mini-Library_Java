package exception;

import interfaces.ILoanException;
import interfaces.IValidationException;

import java.time.LocalDate;

/**
* Wird geworfen, wenn das Ausleihdatum in der Zukunft liegt.
*/
public class LoanDateInFutureException extends RuntimeException implements ILoanException, IValidationException {
 public LoanDateInFutureException(LocalDate date) {
     super("Ausleihdatum liegt in der Zukunft: " + date);
 }
}
