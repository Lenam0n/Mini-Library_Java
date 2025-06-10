package interfaces.utils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import entities.Book;
import entities.Member;
import entities.Loan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON‐Lese‐Utility als Interface mit statischen Implementierungen.
 */
public interface JsonUtil {

    /**
     * Liest eine Liste von Book‐Objekten aus der JSON‐Datei.
     */
    static List<Book> readBooks(String path) throws IOException {
        try (InputStream fis = new FileInputStream(path);
             JsonReader reader = Json.createReader(fis)) {
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

    /**
     * Liest eine Liste von Member‐Objekten aus der JSON‐Datei.
     */
    static List<Member> readMembers(String path) throws IOException {
        try (InputStream fis = new FileInputStream(path);
             JsonReader reader = Json.createReader(fis)) {
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

    /**
     * Liest eine Liste von Loan‐Objekten aus der JSON‐Datei.
     */
    static List<Loan> readLoans(String path) throws IOException {
        try (InputStream fis = new FileInputStream(path);
             JsonReader reader = Json.createReader(fis)) {
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
}
