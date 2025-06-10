package entities;

import interfaces.models.IBookModel;
import interfaces.models.IEntity;
import interfaces.utils.Validatable;
import interfaces.validators.BookValidator;
import global.Result;

public class Book implements IEntity<String>, IBookModel, Validatable<Book,BookValidator> {
    private String isbn;
    private String title;
    private String author;

    public Book() { /* für JSON-Deserialisierung benötigt */ }

    public Book(String isbn, String title, String author) {
        this.isbn   = isbn;
        this.title  = title;
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

    // --- neu implementiert: ---
    @Override
    public BookValidator getValidator() {
        return BookValidator.INSTANCE;
    }

    // optional: delegieren auf das Default der Schnittstelle
    @Override
    public Result<Book, RuntimeException> validate() {
        return Validatable.super.validate();
    }
}
