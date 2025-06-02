package in_memory_repository;

import in_memory_repository.interfaces.IOverdueService;

public class OverdueService implements IOverdueService {
	 private final long GRACE_DAYS;
	 private final double DAY_FEE;
	 
	 private final BorrowService borrowService;
	
	 public OverdueService(BorrowService borrowService) {
	     this.borrowService = borrowService;
	     this.GRACE_DAYS = 30;
	     this.DAY_FEE = 0.5;
	 }
	
	 @Override
	 public double calculateFee(String isbn, long daysBorrowed) {
	     return (daysBorrowed <= GRACE_DAYS) ? 0.0 : (daysBorrowed - GRACE_DAYS) * DAY_FEE;
	 }
	
	 @Override
	 public Loan findActiveLoan(String isbn) {
	     return borrowService.getActiveLoans()
	    		 			 .getOrDefault(isbn, null);
	 }
}
