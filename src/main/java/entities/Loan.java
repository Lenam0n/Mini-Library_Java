// Datei: entities/Loan.java
package entities;

import interfaces.models.IEntity;
import interfaces.utils.Validatable;
import interfaces.validators.LoanValidator;
import global.Result;

import java.time.LocalDate;

public class Loan implements IEntity<String> , Validatable<Loan, LoanValidator> {
    private final String isbn;
    private final Long memberId;
    private final LocalDate borrowDate;

    public Loan(String isbn, Long memberId, LocalDate borrowDate) {
        this.isbn       = isbn;
        this.memberId   = memberId;
        this.borrowDate = borrowDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getMemberId() {
        return memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    @Override
    public String toString() {
        return String.format("Loan[ISBN=%s, MemberID=%d, BorrowDate=%s]", isbn, memberId, borrowDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan other = (Loan) o;
        return isbn.equals(other.isbn) && memberId.equals(other.memberId);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode() * 31 + memberId.hashCode();
    }
    
    @Override
    public LoanValidator getValidator() {
        return LoanValidator.INSTANCE;
    }

    @Override
    public Result<Loan, RuntimeException> validate() {
        return Validatable.super.validate();
    }

    //!TODO ggf noch um eigene ID erweitern
	@Override
	public String getId() {
		return this.isbn;
		 
	}
}
