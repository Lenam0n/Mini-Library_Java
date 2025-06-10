package exceptions;

import interfaces.execptions.ILoanException;
import interfaces.execptions.IValidationException;

/**
* Wird geworfen, wenn die MemberID null ist.
*/
public class LoanMemberIdNullException extends RuntimeException implements ILoanException, IValidationException {
 public LoanMemberIdNullException() {
     super("MemberID darf nicht null sein");
 }
}
