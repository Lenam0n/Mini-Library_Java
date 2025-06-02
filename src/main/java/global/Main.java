// Datei: global/Main.java
package global;

import java.util.List;
import java.util.ArrayList;

import entities.Book;
import entities.Loan;
import entities.Member;
import interfaces.ISearchableRepository;
import services.BorrowService;
import services.ErrorService;
import services.LibraryService;
import services.SearchService;
import services.ReportingService;
import services.PrintService;
import utils.DataLoader;


/**
 * Einstiegspunkt der Anwendung. Try/Catch-Blöcke sind weitgehend entfernt,
 * da ErrorService sie zentral übernimmt.
 */
public class Main {
    public static void main(String[] args) {
        // 1) Repositories einrichten
        ISearchableRepository<Book, String> bookRepo   = new Repository<>();
        ISearchableRepository<Member, Long> memberRepo = new Repository<>();

        // 2) Daten aus JSON-Dateien einlesen
        List<Book> books = ErrorService.execute(() -> DataLoader.loadBooks("data/books.json"));
        List<Member> members = ErrorService.execute(() -> DataLoader.loadMembers("data/members.json"));

        if (books != null) {
            for (Book b : books) bookRepo.save(b);
        }
        if (members != null) {
            for (Member m : members) memberRepo.save(m);
        }

        // 3) Services instanziieren
        LibraryService   libraryService  = new LibraryService(bookRepo, memberRepo);
        BorrowService    borrowService   = new BorrowService(bookRepo, memberRepo);
        SearchService    searchService   = new SearchService(bookRepo, memberRepo);
        ReportingService reportingService= new ReportingService();
        PrintService     printService    = new PrintService();

        // 4) CRUD-Listen und Einzel-Suche
        printService.printList("Alle Bücher:", libraryService.findAllBooks());
        Book foundBook = ErrorService.execute(() ->
            libraryService.findBookByIsbn("978-0201633610")
        );
        printService.printFound(foundBook, "Buch nicht gefunden", "978-0201633610");

        printService.printList("Alle Mitglieder:", libraryService.findAllMembers());
        Member foundMember = ErrorService.execute(() ->
            libraryService.findMemberById(3L)
        );
        printService.printFound(foundMember, "Mitglied nicht gefunden", 3L);

        // 5) Leih-Service
        ErrorService.run(() ->
            borrowService.borrowBook("978-0132350884", 1L)
        );

        // 6) Such-Funktionen
        searchService.printBooksByAuthor("Martin");
        searchService.printBooksByTitle("Action");
        searchService.printMembersByName("Alice");

        // 7) Reports generieren
        List<Loan> activeLoans = new ArrayList<>(borrowService.getActiveLoans().values());
        reportingService.printLoanReport(activeLoans);
        reportingService.printBookReport(libraryService.findAllBooks());
        reportingService.printMemberReport(libraryService.findAllMembers());
    }
}
