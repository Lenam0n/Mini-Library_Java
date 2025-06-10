package global;

import interfaces.IRepository;
import services.ErrorService;
import entities.Book;
import entities.Member;
import entities.Loan;
import exception.RepositoryInitializationException;
import utils.DataLoader;

public final class Repositories {
    public static IRepository<Book, String> BOOK;
    public static IRepository<Member, Long> MEMBER;
    public static IRepository<Loan, String> LOAN;

    private Repositories() {}

    public static void init() {
        BOOK   = ErrorService.execute(DataLoader::createBookRepository);
        MEMBER = ErrorService.execute(DataLoader::createMemberRepository);
        LOAN   = ErrorService.execute(DataLoader::createLoanRepository); // NEU

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
