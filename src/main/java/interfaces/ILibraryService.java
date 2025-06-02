package interfaces;

import java.util.List;

import entities.Book;
import entities.Member;

/**
 * Interface für alle allgemeinen Bibliotheks-Operationen
 * (Buchverwaltung, Mitgliederverwaltung).
 */
public interface ILibraryService {

    // --- Buch-Operationen ---
    List<Book> findAllBooks();
    Book findBookByIsbn(String isbn);
    void deleteBookByIsbn(String isbn);
    void saveBook(Book book);
    List<Book> searchBooksByAuthor(String author);
    List<Book> searchBooksByTitle(String title);

    // --- Mitglieder-Operationen ---
    List<Member> findAllMembers();
    Member findMemberById(Long id);
    void deleteMemberById(Long id);
    void saveMember(Member member);
    List<Member> searchMembersByName(String name);
    List<Member> searchMembersByEmail(String email);
}
