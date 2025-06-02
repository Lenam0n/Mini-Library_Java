// Datei: services/LibraryService.java
package services;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import entities.Book;
import entities.Member;
import exeption.BookNotFoundException;
import exeption.MemberNotFoundException;
import global.Result;
import interfaces.IEntity;
import interfaces.IExceptionSupplier;
import interfaces.ILibraryService;
import interfaces.IPrinter;
import interfaces.ISearchableRepository;
import interfaces.IThrowableSupplier;
import interfaces.IValidator;
import utils.ValidationUtils;
import validators.BookValidator;
import validators.MemberValidator;

/**
 * Service‐Klasse für Buch‐ und Mitglieder‐Operationen.
 * Verwendet ErrorService zum zentralen Exception‐Handling
 * und Validator‐Klassen für Validierungen.
 */
public class LibraryService implements ILibraryService {

    private final ISearchableRepository<Book, String>  bookRepo;
    private final ISearchableRepository<Member, Long> memberRepo;

    // IExceptionSupplier, um gezielt RuntimeExceptions zu erzeugen
    private final IThrowableSupplier<String> bookNotFoundEx = isbn ->
        new BookNotFoundException("Buch mit ISBN " + isbn + " nicht gefunden");

    private final IThrowableSupplier<Long> memberNotFoundEx = id ->
        new MemberNotFoundException("Mitglied mit ID " + id + " nicht gefunden");

    // Validator-Instanzen
    private final IValidator<Book>   bookValidator   = new BookValidator();
    private final IValidator<Member> memberValidator = new MemberValidator();

    public LibraryService(
        ISearchableRepository<Book, String>  bookRepo,
        ISearchableRepository<Member, Long> memberRepo
    ) {
        this.bookRepo   = bookRepo;
        this.memberRepo = memberRepo;
    }

    // Hilfsmethode für generische Filterung
    private <E extends IEntity<I>, I> List<E> searchBy(
        ISearchableRepository<E, I> repo,
        Function<E, String> extractor,
        String substring
    ) {
        String lower = substring.toLowerCase();
        return repo.findBy(e -> extractor.apply(e).toLowerCase().contains(lower));
    }

    // --- Buch-Operationen ---

    @Override
    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        // Wrapped in ErrorService.execute, gibt null bei Fehler
        return ErrorService.execute(() ->
            bookRepo.findBy(
                b -> b.getId().equals(isbn),
                () -> bookNotFoundEx.getException(isbn)
            )
        );
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        // Find und Löschen in ErrorService.run, um Exception zentral zu behandeln
        ErrorService.run(() -> {
            // Löst BookNotFoundException aus, falls nicht existiert
            bookRepo.findBy(
                b -> b.getId().equals(isbn),
                () -> bookNotFoundEx.getException(isbn)
            );
            bookRepo.deleteById(isbn);
            IPrinter.out("Buch mit ISBN " + isbn + " erfolgreich gelöscht");
        });
    }

    @Override
    public void saveBook(Book book) {
        // 1) Validierung mit BookValidator
        Result<Book, String> validation = bookValidator.validate(book);
        if (validation.isError()) {
            IPrinter.err("Fehler Speicherung Buch: " + validation.getError());
            return;
        }

        // 2) ISBN‐Format-Check (zusätzlich, falls noch nötig)
        //    BookValidator deckt Formatprüfung bereits ab, kann hier entfallen

        // 3) Speichern
        bookRepo.save(book);
        IPrinter.out("Buch erfolgreich gespeichert: " + book);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return searchBy(bookRepo, Book::getAuthor, author);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return searchBy(bookRepo, Book::getTitle, title);
    }

    // --- Mitglieder-Operationen ---

    @Override
    public List<Member> findAllMembers() {
        return memberRepo.findAll();
    }

    @Override
    public Member findMemberById(Long id) {
        return ErrorService.execute(() ->
            memberRepo.findBy(
                m -> m.getId().equals(id),
                () -> memberNotFoundEx.getException(id)
            )
        );
    }

    @Override
    public void deleteMemberById(Long id) {
        ErrorService.run(() -> {
            // Löst MemberNotFoundException aus, falls nicht existiert
            memberRepo.findBy(
                m -> m.getId().equals(id),
                () -> memberNotFoundEx.getException(id)
            );
            memberRepo.deleteById(id);
            IPrinter.out("Mitglied mit ID " + id + " erfolgreich gelöscht");
        });
    }

    @Override
    public void saveMember(Member member) {
        Result<Member, String> validation = memberValidator.validate(member);
        if (validation.isError()) {
            IPrinter.err("Fehler Speicherung Member: " + validation.getError());
            return;
        }

        memberRepo.save(member);
        IPrinter.out("Member erfolgreich gespeichert: " + member);
    }

    @Override
    public List<Member> searchMembersByName(String nameSubstring) {
        return searchBy(memberRepo, Member::getName, nameSubstring);
    }

    @Override
    public List<Member> searchMembersByEmail(String emailSubstring) {
        return searchBy(memberRepo, Member::getEmail, emailSubstring);
    }
}
