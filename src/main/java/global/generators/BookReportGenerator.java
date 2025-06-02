package global.generators;

import entities.Book;
import interfaces.IReportGenerator;
/*import global.Book;*/

/**
 * Generiert einen Report für Book-Objekte.
 * Implementiert nur header(), formatItem(...) und footer(...).
 * Die Default‐Methode generate(List<T>) im Interface kümmert sich um den
 * eigentlichen Report‐Ablauf (Header → Items → Footer).
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
        String isbn   = book.getIsbn();
        String title  = book.getTitle();
        String author = book.getAuthor();

        String paddedIsbn = String.format("%-15s", isbn);
        String paddedTitle = title.length() > 30
            ? title.substring(0, 27) + "..."
            : String.format("%-30s", title);

        return String.format("%s | %s | %s", paddedIsbn, paddedTitle, author);
    }

    @Override
    public String footer(int count) {
        return String.format(
            "--------------------------------------------------------------\n" +
            "Anzahl Bücher: %d", count
        );
    }
}
