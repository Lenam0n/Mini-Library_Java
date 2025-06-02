package in_memory_repository.interfaces;

import in_memory_repository.Loan;
import java.time.temporal.ChronoUnit;

public interface IOverdueService {
	  double calculateFee(String isbn, long daysBorrowed);
	  default void demandFee(String isbn) {
	        Loan loan = findActiveLoan(isbn);
	        long days = ChronoUnit.DAYS.between(
			        						loan.getBorrowDate()
			                    		  , java.time.LocalDate.now());
	        double fee = calculateFee(isbn, days);
	        if (fee > 0) { System.out.printf("Buch %s: %,.2f € Mahngebühr (ausgeliehen %d Tage)%n", isbn, fee, days); } 
	        else { System.out.printf("Buch %s: keine Gebühr (ausgeliehen %d Tage)%n", isbn, days); }
	    }

	  Loan findActiveLoan(String isbn);
	}

