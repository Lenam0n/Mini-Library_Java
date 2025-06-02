package in_memory_repository.interfaces;

import in_memory_repository.Book;
import in_memory_repository.Member;

import java.util.List;

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
