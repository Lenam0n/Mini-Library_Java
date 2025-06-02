package services;

import interfaces.ISearchableRepository;
import interfaces.IPrinter;

import java.util.List;

import entities.Book;
import entities.Member;

/**
* Kapselt alle Suchoperationen (nach Autor, nach Titel, nach Name, nach Email).
* Benutzt IPrinter, um Ergebnisse sofort auszugeben.
*/
public class SearchService {
 private final ISearchableRepository<Book, String> bookRepo;
 private final ISearchableRepository<Member, Long> memberRepo;

 public SearchService(ISearchableRepository<Book, String> bookRepo,
                      ISearchableRepository<Member, Long> memberRepo) {
     this.bookRepo   = bookRepo;
     this.memberRepo = memberRepo;
 }

 public void printBooksByAuthor(String authorSubstring) {
     List<Book> results = bookRepo.findBy(
         book -> book.getAuthor().toLowerCase().contains(authorSubstring.toLowerCase())
     );
     IPrinter.out("Bücher von Autor '" + authorSubstring + "':");
     results.forEach(b -> IPrinter.out("  " + b));
     IPrinter.out("");
 }

 public void printBooksByTitle(String titleSubstring) {
     List<Book> results = bookRepo.findBy(
         book -> book.getTitle().toLowerCase().contains(titleSubstring.toLowerCase())
     );
     IPrinter.out("Bücher mit '" + titleSubstring + "' im Titel:");
     results.forEach(b -> IPrinter.out("  " + b));
     IPrinter.out("");
 }

 public void printMembersByName(String nameSubstring) {
     List<Member> results = memberRepo.findBy(
         m -> m.getName().toLowerCase().contains(nameSubstring.toLowerCase())
     );
     IPrinter.out("Mitglieder mit Name '" + nameSubstring + "':");
     results.forEach(m -> IPrinter.out("  " + m));
     IPrinter.out("");
 }

 public void printMembersByEmail(String emailSubstring) {
     List<Member> results = memberRepo.findBy(
         m -> m.getEmail().toLowerCase().contains(emailSubstring.toLowerCase())
     );
     IPrinter.out("Mitglieder mit Email '" + emailSubstring + "':");
     results.forEach(m -> IPrinter.out("  " + m));
     IPrinter.out("");
 }
}
