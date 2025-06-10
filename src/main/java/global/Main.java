// Datei: global/Main.java
package global;

import java.util.List;
import java.util.ArrayList;

import entities.Book;
import entities.Member;
import interfaces.services.BorrowService;
import interfaces.services.ErrorService;
import interfaces.services.LibraryService;
import interfaces.services.ReportingService;
import interfaces.services.RepositoryPrintService;
import entities.Loan;

/**
 * Einstiegspunkt der Anwendung – ganz ohne zentrale Services-Klasse.
 * Wir initialisieren die Repositories und erzeugen direkt
 * Instanzen der Service‐Interfaces via anonyme Implementierungen.
 */
public class Main {
    public static void main(String[] args) {
        // 1) Repositories laden
        Repositories.init();

        // 2) Service‐Instanzen anlegen
        LibraryService       library = new LibraryService() {};
        BorrowService        borrow  = new BorrowService() {};
        RepositoryPrintService search  = new RepositoryPrintService() {};
        ReportingService     report  = new ReportingService() {};

        // --- 1) Alle Bücher ausgeben ---
        library.printList("Alle Bücher:", library.findAllBooks());

        // --- 2) Buch per ISBN suchen und ausgeben ---
        Book foundBook = library.findBookByIsbn("978-0201633610");
        library.printFound(foundBook, "Buch nicht gefunden", "978-0201633610");

        // --- 3) Alle Mitglieder ausgeben ---
        library.printList("Alle Mitglieder:", library.findAllMembers());

        // --- 4) Mitglied per ID suchen und ausgeben ---
        Member foundMember = library.findMemberById(3L);
        library.printFound(foundMember, "Mitglied nicht gefunden", 3L);

        // --- 5) Buch ausleihen ---
        borrow.borrowBook("978-0132350884", 1L);

        // --- 6) Suchfunktionen nutzen ---
        search.printBooksByAuthor("Martin");
        search.printBooksByTitle("Action");
        search.printMembersByName("Alice");
        search.printMembersByEmail("example@example.com");

        // --- 7) Reports erstellen ---
        List<Loan> activeLoans = new ArrayList<>(borrow.getActiveLoans().values());
        report.printLoanReport(activeLoans);
        report.printBookReport(library.findAllBooks());
        report.printMemberReport(library.findAllMembers());
    }
}
