// Datei: in_memory_repository/LibraryService.java
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
import interfaces.IValidator;
import utils.ValidationUtils;

/**
 * Service‐Klasse für Buch‐ und Mitglieder‐Operationen.
 * Verwendet ISearchableRepository.findBy(...) mit IExceptionSupplier, um Exceptions zentral zu definieren.
 */
public class LibraryService implements ILibraryService {

    private final ISearchableRepository<Book, String> bookRepo;
    private final ISearchableRepository<Member, Long> memberRepo;

    // Functional Interfaces für Ausnahme-Erzeugung
    private final IExceptionSupplier<String> bookNotFoundEx = id ->
        new BookNotFoundException("Buch mit ISBN " + id + " nicht gefunden");

    private final IExceptionSupplier<Long> memberNotFoundEx = id ->
        new MemberNotFoundException("Mitglied mit ID " + id + " nicht gefunden");

    // Validierungs‐Validatoren für Book
    private final IValidator<Book> titleNotEmpty = book ->
        (book.getTitle() != null && !book.getTitle().isBlank())
            ? Result.success(book)
            : Result.error("Titel darf nicht leer sein");

    private final IValidator<Book> authorNotEmpty = book ->
        (book.getAuthor() != null && !book.getAuthor().isBlank())
            ? Result.success(book)
            : Result.error("Autor darf nicht leer sein");

    private final IValidator<Book> isbnFormatValid = book -> {
        String isbn = book.getIsbn();
        Pattern p = Pattern.compile("^\\d{3}-\\d{10}$");
        if (isbn != null && p.matcher(isbn).matches()) {
            return Result.success(book);
        }
        return Result.error("Ungültige ISBN: " + isbn);
    };

    // Validierungs‐Validatoren für Member
    private final IValidator<Member> memberNameNotEmpty = member ->
        (member.getName() != null && !member.getName().isBlank())
            ? Result.success(member)
            : Result.error("Name darf nicht leer sein");

    private final IValidator<Member> memberEmailValid = member -> {
        String email = member.getEmail();
        if (email != null && email.contains("@") && email.contains(".")) {
            return Result.success(member);
        }
        return Result.error("Ungültige E-Mail: " + email);
    };

    public LibraryService(
        ISearchableRepository<Book, String> bookRepo,
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
        return bookRepo.findBy(
            b -> b.getId().equals(isbn),
            () -> bookNotFoundEx.create(isbn)
        );
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        bookRepo.findBy(
            b -> b.getId().equals(isbn),
            () -> bookNotFoundEx.create(isbn)
        );
        bookRepo.deleteById(isbn);
    }

    @Override
    public void saveBook(Book book) {
        Result<Book, String> r1 = ValidationUtils.validate(book, titleNotEmpty);
        if (r1.isError()) {
            IPrinter.err("Fehler Speicherung Buch: " + r1.getError());
            return;
        }
        Result<Book, String> r2 = ValidationUtils.validate(book, authorNotEmpty);
        if (r2.isError()) {
            IPrinter.err("Fehler Speicherung Buch: " + r2.getError());
            return;
        }
        Result<Book, String> r3 = ValidationUtils.validate(book, isbnFormatValid);
        if (r3.isError()) {
            IPrinter.err("Fehler Speicherung Buch: " + r3.getError());
            return;
        }
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
        return memberRepo.findBy(
            m -> m.getId().equals(id),
            () -> memberNotFoundEx.create(id)
        );
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepo.findBy(
            m -> m.getId().equals(id),
            () -> memberNotFoundEx.create(id)
        );
        memberRepo.deleteById(id);
    }

    @Override
    public void saveMember(Member member) {
        Result<Member, String> r1 = ValidationUtils.validate(member, memberNameNotEmpty);
        if (r1.isError()) {
            IPrinter.err("Fehler Speicherung Member: " + r1.getError());
            return;
        }
        Result<Member, String> r2 = ValidationUtils.validate(member, memberEmailValid);
        if (r2.isError()) {
            IPrinter.err("Fehler Speicherung Member: " + r2.getError());
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
