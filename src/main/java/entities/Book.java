// Datei: in_memory_repository/Book.java
package entities;

import interfaces.IEntity;
import model.IBookModel;

public class Book implements IEntity<String>, IBookModel {
    private String isbn;
    private String title;
    private String author;

    public Book() { /* für JSON‐Deserialisierung benötigt */ }

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    @Override
    public String getId() {
        return this.isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return String.format("Book[isbn=%s, title=%s, author=%s]", isbn, title, author);
    }
}
