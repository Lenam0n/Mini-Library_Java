package in_memory_repository;

import in_memory_repository.interfaces.IEntity;

import java.time.LocalDate;

/**
 * Repräsentiert eine Ausleihe: welches Buch (ISBN) von welchem Mitglied (ID)
 * seit wann ausgeliehen ist.
 */
public class Loan {
    private final String isbn;         // Die ISBN des ausgeliehenen Buchs
    private final Long memberId;       // Die ID des Mitglieds
    private final LocalDate borrowDate; // Datum der Ausleihe

    /**
     * Konstruktor.
     *
     * @param isbn       ISBN des Buchs
     * @param memberId   ID des Mitglieds
     * @param borrowDate Datum, an dem das Buch entliehen wurde
     */
    public Loan(String isbn, Long memberId, LocalDate borrowDate) {
        this.isbn       = isbn;
        this.memberId   = memberId;
        this.borrowDate = borrowDate;
    }

    /** Die ISBN des ausgeliehenen Buchs. */
    public String getIsbn() {
        return isbn;
    }

    /** Die ID des Mitglieds, das ausgeliehen hat. */
    public Long getMemberId() {
        return memberId;
    }

    /** Das Datum, an dem die Ausleihe begann. */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    @Override
    public String toString() {
        return String.format(
            "Loan[ISBN=%s, MemberID=%d, BorrowDate=%s]",
            isbn, memberId, borrowDate
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan other = (Loan) o;
        // Zwei Loans als gleich betrachten, wenn ISBN und MemberID identisch sind
        return isbn.equals(other.isbn) &&
               memberId.equals(other.memberId);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode() * 31 + memberId.hashCode();
    }
}