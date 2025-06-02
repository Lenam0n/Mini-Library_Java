package in_memory_repository;

import in_memory_repository.interfaces.IValidator;

import java.time.LocalDate;

/**
 * Validiert alle relevanten Felder einer Loan-Instanz:
 *  - isbn darf nicht null sein (Existenz-Check im Service)
 *  - memberId darf nicht null sein (Existenz-Check im Service)
 *  - borrowDate darf nicht in der Zukunft liegen
 */
public class LoanValidator implements IValidator<Loan> {

    @Override
    public Result<Loan, String> validate(Loan loan) {
        if (loan == null) {
            return Result.error("Loan darf nicht null sein");
        }

        // 1) ISBN nicht null (Service sollte später Existenz prüfen)
        if (loan.getIsbn() == null || loan.getIsbn().isBlank()) {
            return Result.error("ISBN darf nicht null/leer sein");
        }

        // 2) memberId nicht null
        if (loan.getMemberId() == null) {
            return Result.error("MemberID darf nicht null sein");
        }

        // 3) borrowDate nicht in der Zukunft
        LocalDate date = loan.getBorrowDate();
        if (date == null) {
            return Result.error("Ausleihdatum darf nicht null sein");
        }
        if (date.isAfter(LocalDate.now())) {
            return Result.error("Ausleihdatum liegt in der Zukunft: " + date);
        }

        // Alle Prüfungen bestanden
        return Result.success(loan);
    }
}
