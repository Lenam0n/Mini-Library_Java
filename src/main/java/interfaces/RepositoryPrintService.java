package interfaces;

import java.util.List;
import java.util.function.Predicate;

import entities.Book;
import entities.Member;
import global.Repositories;
import interfaces.IRepository;       // richtiges Interface importieren
import interfaces.IEntity;
import interfaces.Validatable;      // für den Generic‐Bound
import interfaces.IActions;

/**
 * Interface statt Klasse. Erweitert PrintService, um Druckmethoden direkt zu nutzen.
 * Nutzt nur noch ein einziges IRepository<T,ID> für CRUD, Suche und Validation.
 */
public interface RepositoryPrintService extends PrintService {

    /**
     * Liefert das Book-Repository (statisch aus Repositories).
     */
    default IRepository<Book, String> bookRepo() {
        return Repositories.BOOK;
    }

    /**
     * Liefert das Member-Repository (statisch aus Repositories).
     */
    default IRepository<Member, Long> memberRepo() {
        return Repositories.MEMBER;
    }

    /**
     * Suche Bücher nach Autor und drucke das Ergebnis.
     */
    default void printBooksByAuthor(String author) {
        IActions.run(v -> {
            List<Book> results = filterBy(
                bookRepo(),
                book -> book.getAuthor()
                            .toLowerCase()
                            .contains(author.toLowerCase())
            );
            printHeader("Bücher von Autor", author);
            printResults(results);
            printFooter();
        });
    }

    /**
     * Suche Bücher nach Titel und drucke das Ergebnis.
     */
    default void printBooksByTitle(String title) {
        IActions.run(v -> {
            List<Book> results = filterBy(
                bookRepo(),
                book -> book.getTitle()
                            .toLowerCase()
                            .contains(title.toLowerCase())
            );
            printHeader("Bücher mit", title + " im Titel");
            printResults(results);
            printFooter();
        });
    }

    /**
     * Suche Mitglieder nach Name und drucke das Ergebnis.
     */
    default void printMembersByName(String name) {
        IActions.run(v -> {
            List<Member> results = filterBy(
                memberRepo(),
                m -> m.getName()
                      .toLowerCase()
                      .contains(name.toLowerCase())
            );
            printHeader("Mitglieder mit Name", name);
            printResults(results);
            printFooter();
        });
    }

    /**
     * Suche Mitglieder nach E-Mail und drucke das Ergebnis.
     */
    default void printMembersByEmail(String email) {
        IActions.run(v -> {
            List<Member> results = filterBy(
                memberRepo(),
                m -> m.getEmail()
                      .toLowerCase()
                      .contains(email.toLowerCase())
            );
            printHeader("Mitglieder mit Email", email);
            printResults(results);
            printFooter();
        });
    }

    /**
     * Hilfsmethode: Filtert Repository nach Predicate und liefert die Liste der Treffer.
     * Passt nun zum Generic‐Bound von IRepository<T,ID>.
     */
    default <T extends IEntity<ID> & Validatable<T, ?>, ID> 
        List<T> filterBy(
            IRepository<T, ID> repo,
            Predicate<T> predicate
        ) 
    {
        return repo.findBy(predicate).getValue();
    }
}
