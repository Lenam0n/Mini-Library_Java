package in_memory_repository.interfaces;

import java.util.Map;
import in_memory_repository.Loan;

/**
 * Interface für den Ausleih-Service.
 */
public interface IBorrowService {
    void borrowBook(String isbn, Long memberId);
    void returnBook(String isbn, Long memberId);
    Map<String, Loan> getActiveLoans();
}
