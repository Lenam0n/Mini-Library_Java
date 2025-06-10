package global;

import interfaces.IRepository;
import interfaces.services.ErrorService;
import interfaces.utils.Loader;
import entities.Book;
import entities.Member;
import exceptions.RepositoryInitializationException;
import entities.Loan;

public final class Repositories {
    public static IRepository<Book, String> BOOK;
    public static IRepository<Member, Long> MEMBER;
    public static IRepository<Loan, String> LOAN;

    private Repositories() {}

    public static void init() {
        BOOK   = ErrorService.execute(Loader::createBookRepository);
        MEMBER = ErrorService.execute(Loader::createMemberRepository);
        LOAN   = ErrorService.execute(Loader::createLoanRepository); // NEU

        if (BOOK == null) {
            throw new RepositoryInitializationException("Book-Repository konnte nicht initialisiert werden.");
        }
        if (MEMBER == null) {
            throw new RepositoryInitializationException("Member-Repository konnte nicht initialisiert werden.");
        }
        if (LOAN == null) {
            throw new RepositoryInitializationException("Loan-Repository konnte nicht initialisiert werden."); // NEU
        }
    }
}
