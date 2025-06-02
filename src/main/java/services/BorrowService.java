package services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import entities.Book;
import entities.Loan;
import entities.Member;
import exeption.BookUnavailableException;
import global.Result;
import interfaces.IBorrowService;
import interfaces.IPrinter;
import interfaces.ISearchableRepository;
import interfaces.IValidator;
import utils.ValidationUtils;
import validators.LoanValidator;

/**
 * Service‐Klasse für Ausleihen und Rückgaben.
 * Nutzt ISearchableRepository.findBy(Predicate, Supplier) für ID‐Prüfungen.
 */
public class BorrowService implements IBorrowService {

    private final ISearchableRepository<Book, String> bookRepo;
    private final ISearchableRepository<Member, Long> memberRepo;
    private final IValidator<Loan> loanValidator;
    private final Map<String, Loan> activeLoans = new HashMap<>();

    public BorrowService(
            ISearchableRepository<Book, String> bookRepo,
            ISearchableRepository<Member, Long> memberRepo
    ) {
        this.bookRepo      = bookRepo;
        this.memberRepo    = memberRepo;
        this.loanValidator = new LoanValidator();
    }

    @Override
    public void borrowBook(String isbn, Long memberId) {
        // 1) Existenz prüfen: Buch
        Book book = bookRepo.findBy(
            b -> b.getId().equals(isbn),
            () -> new BookUnavailableException("Buch mit ISBN " + isbn + " nicht gefunden")
        );

        // 2) Existenz prüfen: Mitglied
        Member member = memberRepo.findBy(
            m -> m.getId().equals(memberId),
            () -> new BookUnavailableException("Mitglied mit ID " + memberId + " nicht gefunden")
        );

        // 3) Verfügbarkeit prüfen
        if (activeLoans.containsKey(isbn)) {
            IPrinter.err("Fehler Ausleihung: Buch mit ISBN " + isbn + " ist bereits verliehen");
            return;
        }

        // 4) Loan‐Objekt erstellen und validieren
        Loan loan = new Loan(isbn, memberId, LocalDate.now());
        Result<Loan, String> validationResult = ValidationUtils.validate(loan, loanValidator);
        if (validationResult.isError()) {
            IPrinter.err("Fehler bei Loan‐Validierung: " + validationResult.getError());
            return;
        }

        // 5) Loan speichern
        activeLoans.put(isbn, loan);
        IPrinter.out("Erfolgreich verliehen: " + isbn + " an Mitglied " + memberId);
    }

    @Override
    public void returnBook(String isbn, Long memberId) {
        // 1) Existenz prüfen: Buch
        bookRepo.findBy(
            b -> b.getId().equals(isbn),
            () -> new BookUnavailableException("Buch mit ISBN " + isbn + " nicht gefunden")
        );

        // 2) Existenz prüfen: Mitglied
        memberRepo.findBy(
            m -> m.getId().equals(memberId),
            () -> new BookUnavailableException("Mitglied mit ID " + memberId + " nicht gefunden")
        );

        // 3) War das Buch verliehen?
        Loan loan = activeLoans.get(isbn);
        if (loan == null || !loan.getMemberId().equals(memberId)) {
            throw new BookUnavailableException(
                "Buch mit ISBN " + isbn + " ist nicht an Mitglied " + memberId + " verliehen"
            );
        }

        // 4) Rückgabe durchführen
        activeLoans.remove(isbn);
        IPrinter.out("Erfolgreich zurückgegeben: " + isbn + " von Mitglied " + memberId);
    }

    @Override
    public Map<String, Loan> getActiveLoans() {
        return Map.copyOf(activeLoans);
    }
}
