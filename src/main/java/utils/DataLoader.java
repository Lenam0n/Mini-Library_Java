// Datei: utils/DataLoader.java
package utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import entities.Book;
import entities.Member;
import entities.Loan;
import global.Repository;
import interfaces.IRepository;

/**
 * Lädt initiale Datensätze für Book, Member und Loan aus JSON-Dateien
 * und gibt bereits befüllte Repository-Instanzen zurück.
 */
public class DataLoader {
    private DataLoader() { }
    
    private static final String p = "C:/Users/Lenam0n/Documents/Schule/Programmierung/minilib/src/main/resources/data/";

    /** Pfad-Konstanten in einer Map: „books“, „members“, „loans“ → jeweilige JSON-Dateien */
    public static final Map<String, String> PATHS = Map.of(
        "books",   (p + "books.json"),
        "members", (p + "members.json"),
        "loans",   (p + "loans.json")
    );

    /**
     * Erzeugt und befüllt ein IRepository<Book, String> mit allen Büchern aus der JSON-Datei.
     */
    public static IRepository<Book, String> createBookRepository() throws IOException {
        IRepository<Book, String> repo = new Repository<>();
        List<Book> books = JsonUtils.readBooks(PATHS.get("books"));
        for (Book b : books) repo.save(b);
        return repo;
    }

    /**
     * Erzeugt und befüllt ein IRepository<Member, Long> mit allen Mitgliedern aus der JSON-Datei.
     */
    public static IRepository<Member, Long> createMemberRepository() throws IOException {
        IRepository<Member, Long> repo = new Repository<>();
        List<Member> members = JsonUtils.readMembers(PATHS.get("members"));
        for (Member m : members) repo.save(m);
        return repo;
    }

    /**
     * Erzeugt und befüllt ein IRepository<Loan, String> mit allen Ausleihen aus der JSON-Datei.
     */
    public static IRepository<Loan, String> createLoanRepository() throws IOException {
        IRepository<Loan, String> repo = new Repository<>();
        List<Loan> loans = JsonUtils.readLoans(PATHS.get("loans"));
        for (Loan l : loans) repo.save(l);
        return repo;
    }
}
