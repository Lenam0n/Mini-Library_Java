package interfaces.utils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import entities.Book;
import entities.Member;
import entities.Loan;

import global.Repository;
import interfaces.IRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Kombiniertes Interface für JSON‐Einlesen und Repository‐Erzeugung.
 * Alle Methoden sind als static implementiert und können direkt aufgerufen werden.
 */
public interface Loader {

    /** Basis-Pfad zu den JSON-Dateien */
    String BASE_PATH = "C:/Users/Lenam0n/Documents/Schule/Programmierung/minilib/src/main/resources/data/";

    /** Pfad-Konstanten für die Dateien */
    Map<String, String> PATHS = Map.of(
        "books",   BASE_PATH + "books.json",
        "members", BASE_PATH + "members.json",
        "loans",   BASE_PATH + "loans.json"
    );

    // ===== JSON‐Lese-Methoden =====

    static List<Book> readBooks(String path) throws IOException {
        try ( InputStream fis = new FileInputStream(path);
              JsonReader reader = Json.createReader(fis) ) {

            JsonArray arr = reader.readArray();
            List<Book> list = new ArrayList<>(arr.size());
            for (JsonObject obj : arr.getValuesAs(JsonObject.class)) {
                String isbn   = obj.getString("isbn",   null);
                String title  = obj.getString("title",  null);
                String author = obj.getString("author", null);
                if (isbn != null && title != null && author != null) {
                    list.add(new Book(isbn, title, author));
                }
            }
            return list;
        }
    }

    static List<Member> readMembers(String path) throws IOException {
        try ( InputStream fis = new FileInputStream(path);
              JsonReader reader = Json.createReader(fis) ) {

            JsonArray arr = reader.readArray();
            List<Member> list = new ArrayList<>(arr.size());
            for (JsonObject obj : arr.getValuesAs(JsonObject.class)) {
                Long   id    = obj.containsKey("id")
                               ? obj.getJsonNumber("id").longValue()
                               : null;
                String name  = obj.getString("name",  null);
                String email = obj.getString("email", null);
                if (id != null && name != null && email != null) {
                    list.add(new Member(id, name, email));
                }
            }
            return list;
        }
    }

    static List<Loan> readLoans(String path) throws IOException {
        try ( InputStream fis = new FileInputStream(path);
              JsonReader reader = Json.createReader(fis) ) {

            JsonArray arr = reader.readArray();
            List<Loan> list = new ArrayList<>(arr.size());
            for (JsonObject obj : arr.getValuesAs(JsonObject.class)) {
                String     isbn       = obj.getString("isbn",       null);
                Long       memberId   = obj.containsKey("memberId")
                                      ? obj.getJsonNumber("memberId").longValue()
                                      : null;
                String     dateString = obj.getString("borrowDate", null);
                LocalDate  borrowDate = (dateString != null)
                                       ? LocalDate.parse(dateString)
                                       : null;
                if (isbn != null && memberId != null && borrowDate != null) {
                    list.add(new Loan(isbn, memberId, borrowDate));
                }
            }
            return list;
        }
    }

    // ===== Repository‐Factory‐Methoden =====

    static IRepository<Book, String> createBookRepository() throws IOException {
        IRepository<Book, String> repo = new Repository<>();
        for (Book b : readBooks(PATHS.get("books"))) {
            repo.save(b);
        }
        return repo;
    }

    static IRepository<Member, Long> createMemberRepository() throws IOException {
        IRepository<Member, Long> repo = new Repository<>();
        for (Member m : readMembers(PATHS.get("members"))) {
            repo.save(m);
        }
        return repo;
    }

    static IRepository<Loan, String> createLoanRepository() throws IOException {
        IRepository<Loan, String> repo = new Repository<>();
        for (Loan l : readLoans(PATHS.get("loans"))) {
            repo.save(l);
        }
        return repo;
    }
}
