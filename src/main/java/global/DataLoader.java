package global;

import java.io.IOException;
import java.util.List;

import entities.Book;
import entities.Member;
import utils.JsonUtils;

/**
 * Lädt initiale Datensätze für Book und Member aus JSON-Dateien,
 * indem es JsonUtils mit Java JSON-P einsetzt.
 */
public class DataLoader {

    private DataLoader() { }

    /**
     * Liest alle Bücher aus der angegebenen JSON-Datei.
     *
     * @param path Pfad zur books.json
     * @return List<Book>
     * @throws IOException wenn Lese- oder Parsingfehler auftreten
     */
    public static List<Book> loadBooks(String path) throws IOException {
        return JsonUtils.readBooks(path);
    }

    /**
     * Liest alle Mitglieder aus der angegebenen JSON-Datei.
     *
     * @param path Pfad zur members.json
     * @return List<Member>
     * @throws IOException wenn Lese- oder Parsingfehler auftreten
     */
    public static List<Member> loadMembers(String path) throws IOException {
        return JsonUtils.readMembers(path);
    }
}
