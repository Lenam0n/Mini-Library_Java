package interfaces;

import java.util.Map;

import entities.Loan;

/**
 * Interface für den Ausleih-Service.
 */
public interface IBorrowService {
    void borrowBook(String isbn, Long memberId);
    void returnBook(String isbn, Long memberId);
    Map<String, Loan> getActiveLoans();
}
