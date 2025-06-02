package global;

import java.io.IOException;
import java.util.List;

import entities.Book;
import entities.Loan;
import entities.Member;
import exeption.BookNotFoundException;
import exeption.MemberNotFoundException;
import interfaces.IReportGenerator;
import interfaces.ISearchableRepository;
import services.BorrowService;
import services.LibraryService;
import utils.AppUtils;

public class Main {
	public static void main(String[] args) {
        try {
            // 1) Repositories einrichten
            ISearchableRepository<Book, String> bookRepo   = new Repository<>();
            ISearchableRepository<Member, Long> memberRepo = new Repository<>();

            // 2) Daten aus JSON‐Dateien einlesen
            List<Book> books = DataLoader.loadBooks("data/books.json");
            List<Member> members = DataLoader.loadMembers("data/members.json");

            // 3) In Repository speichern
            for (Book b : books) bookRepo.save(b);
            for (Member m : members) memberRepo.save(m);
            

            // 4) LibraryService initialisieren
            LibraryService libraryService = new LibraryService(bookRepo, memberRepo);

            // 5) CRUD‐Listen und Einzel‐Suche
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

            // 6) Leih‐Service
            BorrowService borrowService = new BorrowService(bookRepo, memberRepo);
            borrowService.borrowBook("978-0132350884", 1L);

            // 7) Such‐Funktionen
            AppUtils.printList("Bücher von Martin:", libraryService.searchBooksByAuthor("Martin"));
            AppUtils.printList("Bücher mit 'Action' im Titel:", libraryService.searchBooksByTitle("Action"));
            AppUtils.printList("Mitglieder 'Alice':", libraryService.searchMembersByName("Alice"));

            // 8) Reports generieren
            IReportGenerator<Loan> loanReportGen = new BookReportGenerator<Loan>();
            AppUtils.printReport("Ausleihreport", loanReportGen,
                    List.copyOf(borrowService.getActiveLoans().values()));

            IReportGenerator<Book> bookReportGen = new IReportGenerator<Book>();
            AppUtils.printReport("Bücherreport", bookReportGen, libraryService.findAllBooks());

            IReportGenerator<Member> memberReportGen = new IReportGenerator<Member>();
            AppUtils.printReport("Mitgliederreport", memberReportGen, libraryService.findAllMembers());

        } catch (IOException e) {
            System.err.println("Fehler beim Einlesen der JSON‐Dateien: " + e.getMessage());
        }
    }
}
