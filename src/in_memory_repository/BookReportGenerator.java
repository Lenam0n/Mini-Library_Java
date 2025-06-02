package in_memory_repository;

import in_memory_repository.interfaces.IReportGenerator;


/**
 * Generiert einen Report für Book-Objekte.
 * Header, einzelne Zeilen und Footer mit Gesamtanzahl.
 */
public class BookReportGenerator implements IReportGenerator<Book> {

    public BookReportGenerator() { }

    @Override
    public String header() {
        return String.format(
            "=== Bücherübersicht ===\n" +
            "ISBN             | Titel                            | Autor\n" +
            "--------------------------------------------------------------"
        );
    }

    @Override
    public String formatItem(Book book) {
        // Spaltenbreiten: ISBN (15), Titel (30), Autor (variabel)
        String isbn = book.getIsbn();
        String title = book.getTitle();
        String author = book.getAuthor();

        // Links-/rechts‐Padding für einheitliche Spalten
        String paddedIsbn = String.format("%-15s", isbn);
        String paddedTitle = title.length() > 30
            ? title.substring(0, 27) + "..."
            : String.format("%-30s", title);

        return String.format("%s | %s | %s", paddedIsbn, paddedTitle, author);
    }

    @Override
    public String footer(int count) {
        return String.format("--------------------------------------------------------------\n" +
                             "Anzahl Bücher: %d", count);
    }
}
