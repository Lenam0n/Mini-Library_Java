// Datei: in_memory_repository/Stater.java
package in_memory_repository;

import in_memory_repository.exeption.BookNotFoundException;
import in_memory_repository.exeption.MemberNotFoundException;
import in_memory_repository.interfaces.IBorrowService;
import in_memory_repository.interfaces.ILibraryService;
import in_memory_repository.interfaces.ISearchableRepository;
import in_memory_repository.interfaces.IReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class Stater {
    public static void main(String[] args) {
        // 1) Repositories einrichten
        ISearchableRepository<Book, String> bookRepo   = new InMemoryRepository<>();
        ISearchableRepository<Member, Long> memberRepo = new InMemoryRepository<>();

        // 2) LibraryService initialisieren und Daten anlegen
        ILibraryService libraryService = new LibraryService(bookRepo, memberRepo);
        libraryService.saveBook(new Book("978-0132350884", "Clean Code", "Robert C. Martin"));
        libraryService.saveBook(new Book("978-0201633610", "Design Patterns", "Gamma et al."));
        libraryService.saveMember(new Member(1L, "Alice Müller", "alice@example.com"));
        libraryService.saveMember(new Member(2L, "Bob Schmidt", "bob@example.com"));

        // 3) CRUD-Listen und Einzel-Suche
        AppUtils.printList("Alle Bücher:", libraryService.findAllBooks());
        try {
            Book found = libraryService.findBookByIsbn("978-0201633610");
            System.out.println(found);
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        AppUtils.printList("Alle Mitglieder:", libraryService.findAllMembers());
        try {
            Member foundMember = libraryService.findMemberById(3L);
            System.out.println(foundMember);
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        // 4) Leih-Service
        IBorrowService borrowService = new BorrowService(bookRepo, memberRepo);
        borrowService.borrowBook("978-0132350884", 1L);

        // 5) Such-Funktionen
        AppUtils.printList(
            "Bücher von Martin:",
            libraryService.searchBooksByAuthor("Martin")
        );
        AppUtils.printList(
            "Bücher mit 'Action' im Titel:",
            libraryService.searchBooksByTitle("Action")
        );
        AppUtils.printList(
            "Mitglieder 'Alice':",
            libraryService.searchMembersByName("Alice")
        );

        // 6) Reports generieren
        // 6a) Ausleihreport
        IReportGenerator<Loan> loanReportGen = new LoanReportGenerator();
        List<Loan> activeLoans = new ArrayList<>(borrowService.getActiveLoans().values());
        AppUtils.printReport("Ausleihreport", loanReportGen, activeLoans);

        // 6b) Bücherreport
        IReportGenerator<Book> bookReportGen = new BookReportGenerator();
        AppUtils.printReport("Bücherreport", bookReportGen, libraryService.findAllBooks());

        // 6c) Mitgliederreport
        IReportGenerator<Member> memberReportGen = new MemberReportGenerator();
        AppUtils.printReport("Mitgliederreport", memberReportGen, libraryService.findAllMembers());
    }
}
