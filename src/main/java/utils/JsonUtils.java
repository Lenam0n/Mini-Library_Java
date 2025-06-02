package utils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import entities.Book;
import entities.Member;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility‐Klasse, um JSON‐Arrays ohne externe Bibliotheken
 * mithilfe von Java JSON-P (javax.json) zu lesen.
 */
public class JsonUtils {

    private JsonUtils() { /* Utility‐Klasse darf nicht instanziiert werden */ }

    /**
     * Liest eine Liste von Book‐Objekten aus der JSON‐Datei.
     * Erwartet ein JSON-Array mit Objekten, die die Felder
     * "isbn", "title" und "author" enthalten.
     *
     * @param path Pfad zur JSON-Datei (z.B. "data/books.json")
     * @return List<Book>
     * @throws IOException falls die Datei nicht gefunden oder nicht lesbar ist
     */
    public static List<Book> readBooks(String path) throws IOException {
        try (InputStream fis = new FileInputStream(path);
             JsonReader reader = Json.createReader(fis)) {

            JsonArray jsonArray = reader.readArray();
            List<Book> books = new ArrayList<>(jsonArray.size());

            for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
                String isbn   = obj.getString("isbn", null);
                String title  = obj.getString("title", null);
                String author = obj.getString("author", null);

                if (isbn != null && title != null && author != null) {
                    books.add(new Book(isbn, title, author));
                }
            }
            return books;
        }
    }

    /**
     * Liest eine Liste von Member‐Objekten aus der JSON-Datei.
     * Erwartet ein JSON-Array mit Objekten, die die Felder
     * "id", "name" und "email" enthalten.
     *
     * @param path Pfad zur JSON-Datei (z.B. "data/members.json")
     * @return List<Member>
     * @throws IOException falls die Datei nicht gefunden oder nicht lesbar ist
     */
    public static List<Member> readMembers(String path) throws IOException {
        try (InputStream fis = new FileInputStream(path);
             JsonReader reader = Json.createReader(fis)) {

            JsonArray jsonArray = reader.readArray();
            List<Member> members = new ArrayList<>(jsonArray.size());

            for (JsonObject obj : jsonArray.getValuesAs(JsonObject.class)) {
                Long   id    = obj.containsKey("id") ? obj.getJsonNumber("id").longValue() : null;
                String name  = obj.getString("name", null);
                String email = obj.getString("email", null);

                if (id != null && name != null && email != null) {
                    members.add(new Member(id, name, email));
                }
            }
            return members;
        }
    }
}
