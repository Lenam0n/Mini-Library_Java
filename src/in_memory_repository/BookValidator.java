package in_memory_repository;

import in_memory_repository.interfaces.IValidator;

import java.util.regex.Pattern;

/**
 * Validiert alle relevanten Felder einer Book-Instanz:
 *  - Titel darf nicht leer sein
 *  - Autor darf nicht leer sein
 *  - ISBN muss dem Muster "978-<10 Ziffern>" folgen
 */
public class BookValidator implements IValidator<Book> {

    // Muster: "XXX-YYYYYYYYYY"
    private static final Pattern ISBN_PATTERN = Pattern.compile("^\\d{3}-\\d{10}$");

    @Override
    public Result<Book, String> validate(Book book) {
        if (book == null) {
            return Result.error("Book darf nicht null sein");
        }

        // 1) Titel nicht leer
        String title = book.getTitle();
        if (title == null || title.isBlank()) {
            return Result.error("Titel darf nicht leer sein");
        }

        // 2) Autor nicht leer
        String author = book.getAuthor();
        if (author == null || author.isBlank()) {
            return Result.error("Autor darf nicht leer sein");
        }

        // 3) ISBN-Format prüfen
        String isbn = book.getIsbn();
        if (isbn == null || !ISBN_PATTERN.matcher(isbn).matches()) {
            return Result.error("Ungültige ISBN: " + isbn);
        }

        // Alle Prüfungen bestanden
        return Result.success(book);
    }
}
