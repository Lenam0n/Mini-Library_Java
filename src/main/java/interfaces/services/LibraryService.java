// Datei: interfaces/LibraryService.java
package interfaces.services;

import java.util.List;

import entities.Book;
import entities.Member;
import exceptions.BookNotFoundException;
import exceptions.MemberNotFoundException;
import global.Repositories;
import global.Result;
import interfaces.IActions;
import interfaces.IPrinter;

/**
 * Service‐Interface für Buch‐ und Mitglieder‐Operationen.
 * Enthält Default‐Implementierungen aller Methoden
 * und nutzt PrintService & IActions für Ausgaben und Fehler‐Handling.
 */
public interface LibraryService extends PrintService {

    // --- Buch‐Operationen ---

    /** Liefert alle Bücher. */
    default List<Book> findAllBooks() {
        return Repositories.BOOK.findAll();
    }

    /**
     * Sucht ein Buch per ISBN oder wirft BookNotFoundException.
     * Wird typischerweise in ErrorService.execute(...) gewrappt.
     */
    default Book findBookByIsbn(String isbn) {
        return ErrorService.execute(() -> {
            Result<List<Book>, ? extends RuntimeException> r =
                Repositories.BOOK.findBy(b -> b.getId().equals(isbn));
            List<Book> list = r.getValue();
            if (list.isEmpty()) {
                throw new BookNotFoundException("Buch mit ISBN " + isbn + " nicht gefunden");
            }
            return list.get(0);
        });
    }

    /** Löscht ein Buch nach ISBN und gibt Meldung aus. */
    default void deleteBookByIsbn(String isbn) {
        IActions.run(v -> {
            // wirft, falls nicht vorhanden
            findBookByIsbn(isbn);
            Repositories.BOOK.deleteById(isbn);
            IPrinter.out("Buch mit ISBN " + isbn + " erfolgreich gelöscht");
        });
    }

    /** Speichert ein Buch nach Validierung oder druckt den Fehler. */
    default void saveBook(Book book) {
        Result<Book, RuntimeException> vr = book.validate();
        if (vr.isError()) {
            IPrinter.err("Fehler Speicherung Buch: " + vr.getError().getMessage());
            return;
        }
        Repositories.BOOK.save(vr.getValue());
        IPrinter.out("Buch erfolgreich gespeichert: " + vr.getValue());
    }

    /** Sucht Bücher nach Autor-Substring und druckt Fehler oder liefert Liste. */
    default List<Book> searchBooksByAuthor(String authorSubstring) {
        String lower = authorSubstring.toLowerCase();
        Result<List<Book>, ? extends RuntimeException> r =
            Repositories.BOOK.findBy(b -> b.getAuthor().toLowerCase().contains(lower));
        if (r.isError()) {
            IPrinter.err(r.getError().getMessage());
            return List.of();
        }
        return r.getValue();
    }

    /** Sucht Bücher nach Titel-Substring. */
    default List<Book> searchBooksByTitle(String titleSubstring) {
        String lower = titleSubstring.toLowerCase();
        Result<List<Book>, ? extends RuntimeException> r =
            Repositories.BOOK.findBy(b -> b.getTitle().toLowerCase().contains(lower));
        if (r.isError()) {
            IPrinter.err(r.getError().getMessage());
            return List.of();
        }
        return r.getValue();
    }


    // --- Mitglieder‐Operationen ---

    /** Liefert alle Mitglieder. */
    default List<Member> findAllMembers() {
        return Repositories.MEMBER.findAll();
    }

    /** Sucht ein Mitglied per ID oder wirft MemberNotFoundException. */
    default Member findMemberById(Long id) {
        return ErrorService.execute(() -> {
            Result<List<Member>, ? extends RuntimeException> r =
                Repositories.MEMBER.findBy(m -> m.getId().equals(id));
            List<Member> list = r.getValue();
            if (list.isEmpty()) {
                throw new MemberNotFoundException("Mitglied mit ID " + id + " nicht gefunden");
            }
            return list.get(0);
        });
    }

    /** Löscht ein Mitglied nach ID und gibt Meldung aus. */
    default void deleteMemberById(Long id) {
        IActions.run(v -> {
            // wirft, falls nicht vorhanden
            findMemberById(id);
            Repositories.MEMBER.deleteById(id);
            IPrinter.out("Mitglied mit ID " + id + " erfolgreich gelöscht");
        });
    }

    /** Speichert ein Mitglied nach Validierung oder druckt den Fehler. */
    default void saveMember(Member member) {
        Result<Member, RuntimeException> vr = member.validate();
        if (vr.isError()) {
            IPrinter.err("Fehler Speicherung Member: " + vr.getError().getMessage());
            return;
        }
        Repositories.MEMBER.save(vr.getValue());
        IPrinter.out("Member erfolgreich gespeichert: " + vr.getValue());
    }

    /** Sucht Mitglieder nach Name-Substring. */
    default List<Member> searchMembersByName(String nameSubstring) {
        String lower = nameSubstring.toLowerCase();
        Result<List<Member>, ? extends RuntimeException> r =
            Repositories.MEMBER.findBy(m -> m.getName().toLowerCase().contains(lower));
        if (r.isError()) {
            IPrinter.err(r.getError().getMessage());
            return List.of();
        }
        return r.getValue();
    }

    /** Sucht Mitglieder nach Email-Substring. */
    default List<Member> searchMembersByEmail(String emailSubstring) {
        String lower = emailSubstring.toLowerCase();
        Result<List<Member>, ? extends RuntimeException> r =
            Repositories.MEMBER.findBy(m -> m.getEmail().toLowerCase().contains(lower));
        if (r.isError()) {
            IPrinter.err(r.getError().getMessage());
            return List.of();
        }
        return r.getValue();
    }
}
