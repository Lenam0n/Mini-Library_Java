// Datei: validators/LoanValidator.java
package interfaces;

import entities.Loan;
import exception.LoanDateInFutureException;
import exception.LoanDateNullException;
import exception.LoanIsbnEmptyException;
import exception.LoanMemberIdNullException;
import exception.LoanNullException;
import global.Result;
import interfaces.IValidator;

import java.time.LocalDate;

public interface LoanValidator extends IValidator<Loan> {

    LoanValidator INSTANCE = new LoanValidator() {
        @Override
        public Result<Loan, RuntimeException> validateEntity(Loan loan) {
            Result<Loan, RuntimeException> r;

            r = checkNull(loan);
            if (r.isError()) return r;

            r = checkIsbn(loan);
            if (r.isError()) return r;

            r = checkMemberId(loan);
            if (r.isError()) return r;

            r = checkBorrowDate(loan);
            if (r.isError()) return r;

            return Result.success(loan);
        }
    };

    static Result<Loan, RuntimeException> checkNull(Loan loan) {
        if (loan == null) {
            return Result.error(new LoanNullException());
        }
        return Result.success(loan);
    }

    static Result<Loan, RuntimeException> checkIsbn(Loan loan) {
        String isbn = loan.getIsbn();
        if (isbn == null || isbn.isBlank()) {
            return Result.error(new LoanIsbnEmptyException());
        }
        return Result.success(loan);
    }

    static Result<Loan, RuntimeException> checkMemberId(Loan loan) {
        Long id = loan.getMemberId();
        if (id == null) {
            return Result.error(new LoanMemberIdNullException());
        }
        return Result.success(loan);
    }

    static Result<Loan, RuntimeException> checkBorrowDate(Loan loan) {
        LocalDate date = loan.getBorrowDate();
        if (date == null) {
            return Result.error(new LoanDateNullException());
        }
        if (date.isAfter(LocalDate.now())) {
            return Result.error(new LoanDateInFutureException(date));
        }
        return Result.success(loan);
    }
}
