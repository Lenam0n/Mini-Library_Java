// Datei: interfaces/BorrowService.java
package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import entities.Book;
import entities.Loan;
import entities.Member;
import global.Repositories;
import global.Result;
import interfaces.IActions;
import exception.BorrowNotAllowedException;
import exception.ReturnNotAllowedException;

/**
 * Interface für Ausleihen und Rückgaben.
 * Implementiert PrintService, damit in den Default-Methoden
 * direkt print(...) aufgerufen werden kann.
 */
public interface BorrowService extends PrintService {

    /**
     * Liefert alle aktiven Loans aus dem persistenten Loan-Repository
     * als Map von ISBN → Loan.
     */
    default Map<String, Loan> activeLoansMap() {
        return Repositories.LOAN.findAll().stream()
            .collect(Collectors.toMap(
                Loan::getIsbn,
                Function.identity(),
                (existing, duplicate) -> existing
            ));
    }

    /**
     * Verleiht ein Buch, wenn sowohl Buch- als auch Mitglieds-Lookup
     * und die Loan-Validierung erfolgreich sind.
     */
    default void borrowBook(String isbn, Long memberId) {
        IActions.run(v -> {
            // Buch suchen
            List<Book> bookList = Repositories.BOOK
                .findBy(b -> b.getId().equals(isbn))
                .getValue();
            Book book = bookList.isEmpty() ? null : bookList.get(0);
            if (book == null) {
                print(Result.error(new BorrowNotAllowedException(isbn)));
                return;
            }

            // Mitglied suchen
            List<Member> memberList = Repositories.MEMBER
                .findBy(m -> m.getId().equals(memberId))
                .getValue();
            Member member = memberList.isEmpty() ? null : memberList.get(0);
            if (member == null) {
                print(Result.error(new BorrowNotAllowedException(isbn)));
                return;
            }

            // Verleih anlegen und validieren
            Loan loan = new Loan(isbn, memberId, LocalDate.now());
            Result<Loan, RuntimeException> vf = loan.validate();
            if (vf.isError()) {
                print(Result.<String, RuntimeException>error(vf.getError()));
                return;
            }

            // Persistenten Loan speichern und Erfolg melden
            Repositories.LOAN.save(vf.getValue());
            print(Result.success(
                "Erfolgreich verliehen: " + isbn + " an Mitglied " + memberId
            ));
        });
    }

    /**
     * Gibt ein verliehenes Buch zurück, wenn es aktuell verliehen ist
     * und zur angegebenen Mitglieds-ID gehört.
     */
    default void returnBook(String isbn, Long memberId) {
        IActions.run(v -> {
            Loan loan = activeLoansMap().get(isbn);
            if (loan == null || !loan.getMemberId().equals(memberId)) {
                print(Result.error(new ReturnNotAllowedException(isbn, memberId)));
                return;
            }

            // Rückgabe durchführen
            Repositories.LOAN.deleteById(isbn);
            print(Result.success(
                "Erfolgreich zurückgegeben: " + isbn + " von Mitglied " + memberId
            ));
        });
    }

    /**
     * Liefert eine unveränderliche Kopie aller aktiven Loans.
     */
    default Map<String, Loan> getActiveLoans() {
        return Map.copyOf(activeLoansMap());
    }
}
